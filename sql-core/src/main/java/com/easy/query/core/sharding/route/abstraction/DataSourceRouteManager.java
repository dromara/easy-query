package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/5 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteManager {
    Collection<String> routeTo(Class<?> entityClass, EntityExpression entityExpression);
    DataSourceRouteRule getRouteRule(Class<?> entityClass);
}
