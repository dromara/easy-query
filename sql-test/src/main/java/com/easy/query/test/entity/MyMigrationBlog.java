package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MyMigrationBlogProxy;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/1/18 16:46
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("my_migration_blog")
@Data
@EntityProxy
public class MyMigrationBlog extends BaseEntity implements ProxyEntityAvailable<MyMigrationBlog , MyMigrationBlogProxy> {

    /**
     * 标题
     */
    @Column(comment = "标题")
    private String title;
    /**
     * 内容
     */
    @Column(comment = "内容")
    private String content;
    /**
     * 博客链接
     */
    @Column(comment = "博客链接")
    private String url;
    /**
     * 点赞数
     */
    @Column(comment = "点赞数")
    private Integer star;
    /**
     * 发布时间
     */
    @Column(comment = "发布时间")
    private LocalDateTime publishTime;
    /**
     * 评分
     */
    @Column(comment = "评分")
    private BigDecimal score;
    /**
     * 状态
     */
    @Column(comment = "状态")
    private Integer status;
    /**
     * 排序
     */
    @Column(comment = "排序")
    private BigDecimal order;
    /**
     * 是否置顶
     */
    @Column(comment = "是否置顶")
    private Boolean isTop;
    /**
     * 是否置顶
     */
    @Column(comment = "是否置顶")
    private Boolean top;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = "title")
    private List<SysUser> users;
}
