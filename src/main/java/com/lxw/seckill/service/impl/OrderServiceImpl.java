package com.lxw.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.lxw.seckill.utils.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Override
    public Order secKill(User user, GoodsVo goodsVo) {
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goodsVo.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        seckillGoodsService.updateById(seckillGoods);
        //创建订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel((byte) 1);
        order.setStatus((byte) 0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrderService.save(seckillOrder);
        return order;

    }
}
