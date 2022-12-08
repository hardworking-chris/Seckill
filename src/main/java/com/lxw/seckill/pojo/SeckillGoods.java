package com.lxw.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxw
 * @since 2022-12-05
 */
@Getter
@Setter
@TableName("t_seckill_goods")
@ApiModel(value = "SeckillGoods对象", description = "")
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("秒杀商品ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("秒杀价")
    private BigDecimal seckillPrice;

    @ApiModelProperty("库存数量")
    private Integer stockCount;

    @ApiModelProperty("秒杀开始时间")
    private Timestamp startDate;

    @ApiModelProperty("秒杀结束时间")
    private Timestamp endDate;
}
