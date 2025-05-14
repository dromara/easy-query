package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: BlogEntity.java
 * @Description: 文件说明
 * create time 2023/3/16 17:23
 */

@Getter
@Setter
@Table("t_blog")
@EntityProxy
@EasyAlias("t_blog")
@ToString
public class BlogEntity extends BaseEntity implements ProxyEntityAvailable<BlogEntity, BlogEntityProxy> {

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

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = "title")
    private List<SysUser> users;
//
//
//    @Column(autoSelect = false)
//    @InsertIgnore
//    @UpdateIgnore
//    private String columnNotInTable;

}
