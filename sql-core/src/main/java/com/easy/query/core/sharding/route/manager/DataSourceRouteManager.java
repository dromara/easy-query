package com.easy.query.core.sharding.route.manager;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.route.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/5 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteManager {
    Collection<String> routeTo(RouteDescriptor routeDescriptor);
    DataSourceRouteRule<?> getRouteRule(Class<?> entityClass);
    boolean addRouteRule(DataSourceRouteRule<?> dataSourceRouteRule);
}
