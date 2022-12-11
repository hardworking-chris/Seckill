package com.lxw.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxw.seckill.exception.GlobalException;
import com.lxw.seckill.mapper.SeckillGoodsMapper;
import com.lxw.seckill.mapper.SeckillOrderMapper;
import com.lxw.seckill.pojo.Order;
import com.lxw.seckill.mapper.OrderMapper;
import com.lxw.seckill.pojo.SeckillGoods;
import com.lxw.seckill.pojo.SeckillOrder;
import com.lxw.seckill.pojo.User;
import com.lxw.seckill.service.IGoodsService;
import com.lxw.seckill.service.IOrderService;
import com.lxw.seckill.service.ISeckillGoodsService;
import com.lxw.seckill.service.ISeckillOrderService;
import com.lxw.seckill.utils.JsonUtil;
import com.lxw.seckill.utils.vo.GoodsVo;
import com.lxw.seckill.utils.vo.OrderDetailVo;
import com.lxw.seckill.utils.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxw
 * @since 2022-12-05
 */
@Service
@Transactional
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Order secKill(User user, GoodsVo goods) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new
                QueryWrapper<SeckillGoods>().eq("goods_id",
                goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        boolean seckillGoodsResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .setSql("stock_count = " + "stock_count-1")
                .eq("goods_id", goods.getId())
                .gt("stock_count", 0)
        );
        if (!seckillGoodsResult) {
            //判断是否还有库存
            return null;
        }
        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel((byte) 1);
        order.setStatus((byte) 0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" +
                        goods.getId(),
                JsonUtil.object2JsonStr(seckillOrder));
        return order;


    }

    @Override
    public OrderDetailVo detail(Long orderId) {
        if (null==orderId){
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsID(order.getGoodsId());
        OrderDetailVo detail = new OrderDetailVo();
        detail.setGoodsVo(goodsVo);
        detail.setOrder(order);
        return detail;

    }
}
