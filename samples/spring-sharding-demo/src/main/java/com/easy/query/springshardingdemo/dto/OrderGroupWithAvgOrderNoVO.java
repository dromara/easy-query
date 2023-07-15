package com.easy.query.springshardingdemo.dto;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

import java.math.BigDecimal;

/**
 * create time 2023/6/26 22:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class OrderGroupWithAvgOrderNoVO {
    private BigDecimal orderNoAvg;
    private String userId;
}
