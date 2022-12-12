package com.lxw.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxw.seckill.pojo.Goods;
import com.lxw.seckill.utils.vo.GoodsVo;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsID(Long goodsID);
}
