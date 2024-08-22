package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.basic.jdbc.types.handler.ObjectTypeHandler;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.Blog2StarToStringColumnValueSQLConverter;
import com.easy.query.test.entity.proxy.Blog2EntityProxy;
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
 * @Date: 2023/3/16 17:23
 */

@Getter
@Setter
@Table("t_blog")
@EntityFileProxy
@ToString
public class Blog2Entity extends BaseEntity implements ProxyEntityAvailable<Blog2Entity , Blog2EntityProxy> {

    /**
     * 标题
     */
    private String title;
    /**
     * 点赞数
     */
    private Integer star;
    @Column(sqlConversion = Blog2StarToStringColumnValueSQLConverter.class)
    @InsertIgnore
    @UpdateIgnore
    private String starStr;

}
