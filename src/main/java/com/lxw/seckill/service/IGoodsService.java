package com.lxw.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxw.seckill.pojo.Goods;
import com.lxw.seckill.utils.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxw
 * @since 2022-12-05
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsID(Long goodsID);
}
