package com.easy.query.test.vo;

import com.easy.query.core.annotation.Column;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * create time 2023/5/24 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@ToString
public class BlogEntityVO2 {

    /**
     * 希望返回Topic的id其他都是Blog的属性
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    @Column(large = true)
    private String content;
    /**
     * 博客链接
     */
    private String url;
    /**
     * 点赞数
     */
    private Integer star;
    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
    /**
     * 评分
     */
    private BigDecimal score;
    /**
     * 状态
     */
    private Integer status;
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
