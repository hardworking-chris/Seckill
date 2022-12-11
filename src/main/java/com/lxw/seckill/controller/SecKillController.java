package com.lxw.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxw.seckill.pojo.Order;
import com.lxw.seckill.pojo.SeckillOrder;
import com.lxw.seckill.pojo.User;
import com.lxw.seckill.service.IGoodsService;
import com.lxw.seckill.service.IOrderService;
import com.lxw.seckill.service.ISeckillOrderService;
import com.lxw.seckill.utils.vo.GoodsVo;
import com.lxw.seckill.utils.vo.RespBean;
import com.lxw.seckill.utils.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀
 */

@Controller
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 秒杀
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value ="/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(Model model, User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
//        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsID(goodsId);


        //判断库存
        if (goods.getStockCount() < 1) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new
                QueryWrapper<SeckillOrder>().eq("user_id",
                user.getId()).eq(
                "goods_id",
                goodsId));
        //判断是否重复抢购
        // SeckillOrder seckillOrder = seckillOrderService.getOne(new
//        QueryWrapper<SeckillOrder>().eq("user_id",
//                //       user.getId()).eq(
//                //       "goods_id",
//                //       goodsId));
        String seckillOrderJson = (String)
                redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (!StringUtils.isEmpty(seckillOrderJson)) {
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        Order order = orderService.secKill(user, goods);
        if (null != order) {
            return RespBean.success(order);
        }
        return RespBean.error(RespBeanEnum.ERROR);

    }

}
