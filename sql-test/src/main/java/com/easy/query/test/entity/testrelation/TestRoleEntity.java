package com.easy.query.test.entity.testrelation;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.testrelation.proxy.TestRoleEntityProxy;
import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.EntityProxy;

import java.util.List;

/**
 * 测试角色 实体类。
 *
 * @author xjm
 * @since 1.0
 */
@Data
@Table(value = "t_test_role")
@EntityProxy
public class TestRoleEntity implements ProxyEntityAvailable<TestRoleEntity, TestRoleEntityProxy> {

    /**
     * 主键ID
     */
    @Column(primaryKey = true)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


    @Navigate(value = RelationTypeEnum.ManyToMany,
            mappingClass = TestJoinEntity.class,
            selfMappingProperty = "firstId",
            targetMappingProperty = "secondId",
            extraFilter = JoinType.class)
    private List<TestRouteEntity> routes;
}

