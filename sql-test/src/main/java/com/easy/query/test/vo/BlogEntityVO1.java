package com.easy.query.test.vo;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * create time 2023/5/24 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@ToString
@EntityFileProxy
public class BlogEntityVO1 {

    /**
     * 评分
     */
    private BigDecimal score;
    /**
     * 状态
     */
    @Column(value = "status")
    private Integer abc;
    /**
     * 排序
     */
    private BigDecimal order;
    /**
     * 是否置顶
     */
    private Boolean isTop;
    /**
     * 是否置顶
     */
    private Boolean top;
}
