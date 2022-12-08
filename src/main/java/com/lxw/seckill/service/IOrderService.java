package com.lxw.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxw.seckill.pojo.Order;
import com.lxw.seckill.pojo.User;
import com.lxw.seckill.utils.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxw
 * @since 2022-12-05
 */
public interface IOrderService extends IService<Order> {

    Order secKill(User user, GoodsVo goodsVo);
}
