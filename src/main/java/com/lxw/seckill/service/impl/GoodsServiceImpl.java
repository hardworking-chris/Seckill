package com.lxw.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxw.seckill.pojo.Goods;
import com.lxw.seckill.mapper.GoodsMapper;
import com.lxw.seckill.service.IGoodsService;
import com.lxw.seckill.utils.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVoByGoodsID(Long goodsID) {
        return goodsMapper.findGoodsVoByGoodsID(goodsID);
    }
}
