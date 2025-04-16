package com.easy.query.core.expression.parser.core.base.core;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

/**
 * create time 2023/11/21 12:09
 * 文件说明
 *
 * @author xuejiaming
 */
public final class FilterContext {
    private  Filter filter;
    private final EntityExpressionBuilder entityExpressionBuilder;

    public FilterContext(Filter filter, EntityExpressionBuilder entityExpressionBuilder){

        this.filter = filter;
        this.entityExpressionBuilder = entityExpressionBuilder;
    }

    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
