package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8User2Proxy;
import com.easy.query.test.mysql8.entity.proxy.M8User3Proxy;
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
@Table("m8_user3")
@FieldNameConstants
public class M8User3 implements ProxyEntityAvailable<M8User3, M8User3Proxy> {
    @Column(primaryKey = true)
    private String userId;
    private String userName;
    private Integer userAge;
    private LocalDateTime createTime;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {Fields.userId},
            selfMappingProperty = {M8UserRole3.Fields.userId},
            mappingClass = M8UserRole3.class,
            targetMappingProperty = {M8UserRole3.Fields.roleId},
            targetProperty = {M8Role3.Fields.roleId})
    private List<M8Role3> roles;


//    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8User2.Fields.userId}, targetProperty = {M8UserBook.Fields.userId})
//    private List<M8UserBook> myBooks;
}
