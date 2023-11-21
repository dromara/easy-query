package com.easy.query.core.expression.parser.core.base.core;

import com.easy.query.core.expression.builder.Filter;

/**
 * create time 2023/11/21 12:09
 * 文件说明
 *
 * @author xuejiaming
 */
public final class FilterContext {
    private  Filter filter;

    public FilterContext(Filter filter){

        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
