package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8User2Proxy;
import com.easy.query.test.mysql8.entity.proxy.M8UserProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/3/15 12:35
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_user2")
@FieldNameConstants
public class M8User2 implements ProxyEntityAvailable<M8User2, M8User2Proxy> {
    @Column(primaryKey = true)
    private String userId;
    private String userName;
    private Integer userAge;
    private LocalDateTime createTime;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {Fields.userId},
            selfMappingProperty = {M8UserRole2.Fields.userId},
            mappingClass = M8UserRole2.class,
            targetMappingProperty = {M8UserRole2.Fields.roleId},
            targetProperty = {M8Role2.Fields.roleId})
    private List<M8Role2> roles;


    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8User2.Fields.userId}, targetProperty = {M8UserBook.Fields.userId})
    private List<M8UserBook> books;
}
