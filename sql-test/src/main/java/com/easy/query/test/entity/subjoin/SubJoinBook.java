package com.easy.query.test.entity.subjoin;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.subjoin.proxy.SubJoinAuthorProxy;
import com.easy.query.test.entity.subjoin.proxy.SubJoinBookProxy;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * create time 2025/2/20 15:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("sub_join_book")
@EntityProxy
public class SubJoinBook implements ProxyEntityAvailable<SubJoinBook, SubJoinBookProxy> {

    @Column(primaryKey = true)
    private String id;

    private BigDecimal price;
    private String uid;
    private String authorId;

    private LocalDateTime createTime;

    private String name;

    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = {SubJoinBookProxy.Fields.authorId}, targetProperty = {SubJoinAuthorProxy.Fields.id})
    private SubJoinAuthor author;

}
