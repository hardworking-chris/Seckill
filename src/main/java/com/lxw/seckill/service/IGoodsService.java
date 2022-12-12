package com.lxw.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxw.seckill.pojo.Goods;
import com.lxw.seckill.utils.vo.GoodsVo;

import java.util.List;

public interface IGoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsID(Long goodsID);
}
