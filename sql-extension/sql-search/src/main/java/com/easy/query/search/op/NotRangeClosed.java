package com.easy.query.search.op;

import com.easy.query.core.enums.SQLRangeEnum;

import java.util.List;

public class NotRangeClosed
        implements Op {
    @Override
    public String getName() {
        return "nrc";
    }

    @Override
    public void filter(FilterContext context) {
        List<Object> params = context.getParams();
        Object left = params.isEmpty() ? null : params.get(0);
        Object right = params.size() < 2 ? null : params.get(1);
        context.notRange(context.getTable(), context.getPropertyName(),
                left != null,
                left,
                right != null,
                right,
                SQLRangeEnum.CLOSED
        );
    }
}
