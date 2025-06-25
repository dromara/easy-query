package com.easy.query.core.api.dynamic.executor.search.op;

import com.easy.query.core.enums.SQLLikeEnum;

public class NotLike implements Op {
    @Override
    public String getName() {
        return "nl";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().notLike(context.getTable(), context.getPropertyName(), context.getParam(), SQLLikeEnum.LIKE_PERCENT_ALL);
    }
}
