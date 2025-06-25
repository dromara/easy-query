package com.easy.query.core.api.dynamic.executor.search.op;

public class LessThan implements Op {
    @Override
    public String getName() {
        return "lt";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().lt(context.getTable(), context.getPropertyName(), context.getParam());
    }
}
