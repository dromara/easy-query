package com.easy.query.core.sharding.route.table;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.route.Route;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/5 13:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRoute extends Route {
    <T> Collection<TableRouteUnit> route(TableRouteRule<T> tableRouteRule, DataSourceRouteResult dataSourceRouteResult, RouteDescriptor routeDescriptor);
}
