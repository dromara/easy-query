package com.easy.query.core.sharding.route.datasource;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/12 12:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRoute {
    Collection<String> route(DataSourceRouteRule dataSourceRouteRule, SqlParserResult sqlParserResult);
}
