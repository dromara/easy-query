package com.easy.query.search.match;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

public class EasyTableIndexMatch
        implements EasyTableMatch {
    private final int tableIndex;

    public EasyTableIndexMatch(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    @Override
    public boolean match(TableAvailable table, int tableIndex) {
        return this.tableIndex == tableIndex;
    }

    @Override
    public String toString() {
        return "EasyTableIndexMatch：" + tableIndex;
    }
}
