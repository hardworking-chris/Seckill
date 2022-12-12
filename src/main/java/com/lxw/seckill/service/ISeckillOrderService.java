package com.lxw.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxw.seckill.pojo.SeckillOrder;
import com.lxw.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxw
 * @since 2022-12-05
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return orderId:成功，-1：秒杀失败，0：排队中
     */
    Long getResult(User user, Long goodsId);
}
