package com.lxw.seckill.controller;

import com.lxw.seckill.pojo.User;
import com.lxw.seckill.service.IOrderService;
import com.lxw.seckill.utils.vo.OrderDetailVo;
import com.lxw.seckill.utils.vo.RespBean;
import com.lxw.seckill.utils.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lxw
 * @since 1.0.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    /**
     * 订单详情
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user,Long orderId){
        if (null==user){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail = orderService.detail(orderId);
        return RespBean.success(detail);
    }
}
