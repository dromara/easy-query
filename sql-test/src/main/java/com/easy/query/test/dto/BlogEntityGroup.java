package com.easy.query.test.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xuejiaming
 * @FileName: BlogEntity.java
 * @Description: 文件说明
 * @Date: 2023/3/16 17:23
 */

@Data
public class BlogEntityGroup {

    private String id;
    private Integer idCount;
    /**
     * 标题
     */
    private String title;
    private Integer titleLength;
    /**
     * 点赞数
     */
    private Integer starSum;
    /**
     * 发布时间
     */
    private LocalDateTime publishTimeMax;
    /**
     * 评分
     */
    private BigDecimal scoreSum;
    /**
     * 状态
     */
    private Integer statusAvg;
    /**
     * 排序
     */
    private BigDecimal orderMin;
}
