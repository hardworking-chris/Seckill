package com.lxw.seckill.utils.vo;

import com.lxw.seckill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author lxw
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}

