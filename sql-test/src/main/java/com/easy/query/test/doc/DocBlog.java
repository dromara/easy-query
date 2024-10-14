package com.easy.query.test.doc;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.proxy.DocBlogProxy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * create time 2024/10/12 15:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Getter
@Setter
@Table("t_blog")
@EntityProxy
@ToString
public class DocBlog implements ProxyEntityAvailable<DocBlog , DocBlogProxy> {
    @Column(primaryKey = true)
    private String id;

    /**
     * 标题
     */
    private String title;
    /**
     * 主题
     */
    private String topic;
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
}
