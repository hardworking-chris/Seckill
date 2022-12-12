package com.lxw.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxw.seckill.pojo.SeckillOrder;
import com.lxw.seckill.pojo.User;


public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 获取秒杀结果
     * orderId:成功，-1：秒杀失败，0：排队中
     */
    Long getResult(User user, Long goodsId);
}
