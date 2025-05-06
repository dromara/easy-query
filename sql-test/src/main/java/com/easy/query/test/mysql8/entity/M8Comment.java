package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8CommentProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/5/6 14:14
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("comment")
public class M8Comment implements ProxyEntityAvailable<M8Comment, M8CommentProxy> {
    @Column(primaryKey = true)
    private String id;
    private String parentId;
    private String content;
    private String username;
    private LocalDateTime time;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8CommentProxy.Fields.id}, targetProperty = {M8CommentProxy.Fields.parentId})
    private List<M8Comment> subComments;
}
