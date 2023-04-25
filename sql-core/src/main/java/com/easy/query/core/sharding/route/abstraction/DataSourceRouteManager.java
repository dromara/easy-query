package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/5 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteManager {
    Collection<String> routeTo(Class<?> entityClass, PrepareParseResult prepareParseResult);
    DataSourceRouteRule getRouteRule(Class<?> entityClass);
    boolean addRouteRule(DataSourceRouteRule dataSourceRouteRule);
}
