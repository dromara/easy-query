package com.easy.query.search.op;

public class Equals
        implements Op {
    @Override
    public String getName() {
        return "eq";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().eq(context.getTable(), context.getPropertyName(), context.getParam());
    }
}
