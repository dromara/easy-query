package com.easy.query.core.api.dynamic.executor.search.match;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

public class EasyTableClassMatch implements EasyTableMatch {
    private final Class<?> tableClass;

    public EasyTableClassMatch(Class<?> tableClass) {
        this.tableClass = tableClass;
    }

    public Class<?> getTableClass() {
        return tableClass;
    }

    @Override
    public boolean match(TableAvailable table, int tableIndex) {
        return tableClass.equals(table.getEntityClass());
    }

    @Override
    public String toString() {
        return "EasyTableClassMatch：" + tableClass.getName();
    }
}
