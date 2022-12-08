package com.lxw.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxw.seckill.pojo.Order;
import com.lxw.seckill.pojo.SeckillOrder;
import com.lxw.seckill.pojo.User;
import com.lxw.seckill.service.IGoodsService;
import com.lxw.seckill.service.IOrderService;
import com.lxw.seckill.service.ISeckillOrderService;
import com.lxw.seckill.utils.vo.GoodsVo;
import com.lxw.seckill.utils.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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


    /**
     * 秒杀
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user, Long goodsId) {
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsID(goodsId);


        //判断库存
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id",
                goodsId));
        if(seckillOrder != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEAT_ERROR.getMessage());
            return"secKillFail";
        }
        Order order = orderService.secKill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return"orderDetail";
    }

}
