package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.SysDeptProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2026/1/30 19:10
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@FieldNameConstants
@Table(value = "sys_dept")
@EntityProxy
public class SysDept  implements ProxyEntityAvailable<SysDept, SysDeptProxy> {

    @Column(primaryKey = true)
    private Long id;

    private Long pid;

    private String name;



    @Navigate(value = RelationTypeEnum.OneToMany,
            selfProperty = {SysDeptProxy.Fields.id},
            targetProperty = {SysDeptProxy.Fields.pid})
    private List<SysDept> children;

}
