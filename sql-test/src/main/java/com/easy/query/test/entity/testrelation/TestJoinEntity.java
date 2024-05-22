package com.easy.query.test.entity.testrelation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.testrelation.proxy.TestJoinEntityProxy;
import lombok.Data;

/**
 * join通用多对多表 实体类。
 *
 * @author xjm
 * @since 1.0
 */
@Data
@Table(value = "t_test_join")
@EntityProxy
public class TestJoinEntity implements ProxyEntityAvailable<TestJoinEntity , TestJoinEntityProxy> {

    /**
     * id
     */
    @Column(primaryKey = true)
    private String firstId;

    /**
     * id
     */
    @Column(primaryKey = true)
    private String secondId;

    /**
     * 关联表多对多 1 用户和角色 2 角色和路由
     */
    @Column(primaryKey = true)
    private Integer type;

}
