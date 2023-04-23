package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/5 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRouteManager {
    Collection<TableRouteUnit> routeTo(Class<?> entityClass, DataSourceRouteResult dataSourceRouteResult, SqlParserResult sqlParserResult);
    TableRouteRule getRouteRule(Class<?> entityClass);
}
