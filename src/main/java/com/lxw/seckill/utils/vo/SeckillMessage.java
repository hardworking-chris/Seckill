package com.lxw.seckill.utils.vo;

import com.lxw.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 秒杀信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {

    private User User;

    private Long goodsId;
}
