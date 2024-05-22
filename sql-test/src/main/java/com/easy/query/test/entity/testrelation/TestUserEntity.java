package com.easy.query.test.entity.testrelation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.testrelation.proxy.TestUserEntityProxy;
import lombok.Data;

import java.util.List;

/**
 * 测试用户 实体类。
 *
 * @author xjm
 * @since 1.0
 */
@Data
@Table(value = "t_test_user")
@EntityProxy
public class TestUserEntity implements ProxyEntityAvailable<TestUserEntity, TestUserEntityProxy> {

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
     * 密码
     */
    private String password;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            mappingClass = TestJoinEntity.class,
            selfMappingProperty = "firstId",
            targetMappingProperty = "secondId",
    extraFilter = JoinType.class)
    private List<TestRoleEntity> roles;

}
