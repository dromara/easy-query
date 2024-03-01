package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.BlogEntity2Proxy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * create time 2024/3/1 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
@Getter
@Setter
@Table("t_blog")
@EntityFileProxy
@ToString
public class BlogEntity2  extends BaseEntity implements ProxyEntityAvailable<BlogEntity2 , BlogEntity2Proxy> {
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
    private BigDecimal star;

    @Override
    public Class<BlogEntity2Proxy> proxyTableClass() {
        return BlogEntity2Proxy.class;
    }
}
