package com.lxw.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxw.seckill.pojo.Goods;
import com.lxw.seckill.utils.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxw
 * @since 2022-12-05
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsID(Long goodsID);
}
