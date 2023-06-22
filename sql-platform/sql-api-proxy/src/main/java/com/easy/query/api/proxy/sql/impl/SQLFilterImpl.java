package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.SQLFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2023/6/22 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFilterImpl implements SQLFilter {
    private final Filter filter;

    public SQLFilterImpl(Filter filter){

        this.filter = filter;
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public SQLFilter and(boolean condition, SQLExpression1<SQLFilter> sqlWherePredicateSQLExpression) {
        return null;
    }

    @Override
    public SQLFilter or(boolean condition, SQLExpression1<SQLFilter> sqlWherePredicateSQLExpression) {
        return null;
    }
}
