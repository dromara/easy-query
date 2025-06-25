package com.easy.query.search.op;

import com.easy.query.core.enums.SQLLikeEnum;

public class LikeMatchRight
        implements Op {
    @Override
    public String getName() {
        return "lmr";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().like(context.getTable(), context.getPropertyName(),
                                 context.getParam(), SQLLikeEnum.LIKE_PERCENT_LEFT);
    }
}
