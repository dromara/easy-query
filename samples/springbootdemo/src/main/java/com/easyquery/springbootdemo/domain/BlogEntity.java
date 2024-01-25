package com.easyquery.springbootdemo.domain;

import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easyquery.springbootdemo.domain.proxy.BlogEntityProxy;
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
@EntityFileProxy
@CacheEntitySchema
public class BlogEntity extends BaseEntity implements CacheKvEntity,ProxyEntityAvailable<BlogEntity , BlogEntityProxy> {

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

    @Override
    public Class<BlogEntityProxy> proxyTableClass() {
        return BlogEntityProxy.class;
    }

    @Override
    public String cacheIdValue() {
        return getId();
    }
}
