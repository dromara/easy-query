package com.easy.query.search.op;

public class LessEquals implements Op {
    @Override
    public String getName() {
        return "le";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().le(context.getTable(), context.getPropertyName(), context.getParam());
    }
}
