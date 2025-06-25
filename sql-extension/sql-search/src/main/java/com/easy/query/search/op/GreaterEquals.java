package com.easy.query.search.op;

public class GreaterEquals implements Op {
    @Override
    public String getName() {
        return "ge";
    }

    @Override
    public void filter(FilterContext context) {
        context.getFilter().ge(context.getTable(), context.getPropertyName(), context.getParam());
    }
}
