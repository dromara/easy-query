package com.easy.query.core.api.dynamic.executor.search.op;

import com.easy.query.core.enums.SQLLikeEnum;

public class Like implements Op {
    @Override
    public String getName() {
        return "lk";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().like(context.getTable(), context.getPropertyName(), context.getParam(), SQLLikeEnum.LIKE_PERCENT_ALL);
    }
}
