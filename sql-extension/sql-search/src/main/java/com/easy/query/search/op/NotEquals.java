package com.easy.query.search.op;

public class NotEquals
        implements Op {
    @Override
    public String getName() {
        return "ne";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().ne(context.getTable(), context.getPropertyName(), context.getParam());
    }
}
