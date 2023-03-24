package com.easy.query.entity;

import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @FileName: BlogEntity.java
 * @Description: 文件说明
 * @Date: 2023/3/16 17:23
 * @author xuejiaming
 */

@Data
@Table("t_blog")
public class BlogEntity extends BaseEntity{

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
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
