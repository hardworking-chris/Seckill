package com.lxw.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxw.seckill.pojo.Order;
import com.lxw.seckill.pojo.User;
import com.lxw.seckill.utils.vo.GoodsVo;
import com.lxw.seckill.utils.vo.OrderDetailVo;

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
    OrderDetailVo detail(Long orderId);

    boolean checkPath(User user, Long goodsId, String path);

    String createPath(User user, Long goodsId);
}
