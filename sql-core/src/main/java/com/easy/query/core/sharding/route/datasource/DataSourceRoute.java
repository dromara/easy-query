package com.easy.query.core.sharding.route.datasource;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/12 12:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRoute {
    Collection<String> route(DataSourceRouteRule dataSourceRouteRule, PrepareParseResult prepareParseResult);
}
