package com.lxw.seckill.utils.vo;

import com.lxw.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品详情返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {


    private User tUser;

    private GoodsVo goodsVo;

    private int secKillStatus;

    private int remainSeconds;


}
