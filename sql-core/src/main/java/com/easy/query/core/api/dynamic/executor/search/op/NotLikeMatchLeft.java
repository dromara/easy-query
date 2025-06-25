package com.easy.query.core.api.dynamic.executor.search.op;

import com.easy.query.core.enums.SQLLikeEnum;

public class NotLikeMatchLeft
        implements Op {
    @Override
    public String getName() {
        return "nlml";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().notLike(context.getTable(), context.getPropertyName(),
                                    context.getParam(), SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }
}
