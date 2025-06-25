package com.easy.query.search.op;

import com.easy.query.core.enums.SQLRangeEnum;

import java.util.List;

public class RangeOpenClosed implements Op {
    @Override
    public String getName() {
        return "roc";
    }

    @Override
    public void filter(FilterContext context) {
        List<Object> params = context.getParams();
        Object left = params.isEmpty() ? null : params.get(0);
        Object right = params.size() < 2 ? null : params.get(1);
        context.range(context.getTable(), context.getPropertyName(),
                left != null,
                left,
                right != null,
                right,
                SQLRangeEnum.OPEN_CLOSED
        );
    }
}
