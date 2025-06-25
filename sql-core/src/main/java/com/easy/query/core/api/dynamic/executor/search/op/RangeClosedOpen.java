package com.easy.query.core.api.dynamic.executor.search.op;

import com.easy.query.core.enums.SQLRangeEnum;

import java.util.List;

public class RangeClosedOpen implements Op {
    @Override
    public String getName() {
        return "rco";
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
                SQLRangeEnum.CLOSED_OPEN
        );
    }
}
