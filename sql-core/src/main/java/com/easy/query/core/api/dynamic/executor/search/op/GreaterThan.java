package com.easy.query.core.api.dynamic.executor.search.op;

public class GreaterThan implements Op {
    @Override
    public String getName() {
        return "gt";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().gt(context.getTable(), context.getPropertyName(), context.getParam());
    }
}
