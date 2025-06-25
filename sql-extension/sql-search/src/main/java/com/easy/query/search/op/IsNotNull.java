package com.easy.query.search.op;

public class IsNotNull
        implements Op {
    @Override
    public String getName() {
        return "inn";
    }

    @Override
    public void filter(FilterContext context) {
        Object value = context.getParam();
        boolean notNull = false;

        if (value != null) {
            if (value instanceof Boolean) {
                notNull = (Boolean) value;
            }
            else if (value instanceof Number) {
                notNull = ((Number) value).intValue() != 0;
            }
            else {
                notNull = !value.toString().equalsIgnoreCase("isNull");
            }
        }

        if (!notNull) {
            context.getFilter().isNull(context.getTable(), context.getPropertyName());
        }
        else {
            context.getFilter().isNotNull(context.getTable(), context.getPropertyName());
        }
    }
}
