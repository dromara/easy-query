package com.easy.query.search.op;

public class NotIn
        implements Op {
    @Override
    public String getName() {
        return "ni";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().notIn(context.getTable(), context.getPropertyName(), context.getParams());
    }
}
