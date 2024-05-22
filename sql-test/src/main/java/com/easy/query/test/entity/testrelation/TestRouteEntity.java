package com.easy.query.test.entity.testrelation;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.testrelation.proxy.TestRouteEntityProxy;
import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.EntityProxy;

/**
 * 测试路由 实体类。
 *
 * @author xjm
 * @since 1.0
 */
@Data
@Table(value = "t_test_route")
@EntityProxy
public class TestRouteEntity implements ProxyEntityAvailable<TestRouteEntity , TestRouteEntityProxy> {

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
     * 请求路径
     */
    private String requestPath;

}
