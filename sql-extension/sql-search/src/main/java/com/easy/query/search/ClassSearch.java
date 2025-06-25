package com.easy.query.search;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.search.match.EasyTableAliasMatch;
import com.easy.query.search.match.EasyTableClassMatch;
import com.easy.query.search.match.EasyTableIndexMatch;
import com.easy.query.search.match.EasyTableMatch;

class ClassSearch {
    private final Class<?> searchClass;
    private final EasyTableMatch tableMatch;

    private ClassSearch(
            @NotNull Class<?> searchClass,
            @NotNull EasyTableMatch tableMatch
    ) {
        this.searchClass = searchClass;
        this.tableMatch = tableMatch;
    }

    public static ClassSearch of(@NotNull Class<?> tableClass) {
        return new ClassSearch(tableClass, new EasyTableClassMatch(tableClass));
    }

    public static ClassSearch of(@NotNull Class<?> tableClass, String tableAlias) {
        return new ClassSearch(tableClass, new EasyTableAliasMatch(tableAlias));
    }

    public static ClassSearch of(@NotNull Class<?> tableClass, int tableIndex) {
        return new ClassSearch(tableClass, new EasyTableIndexMatch(tableIndex));
    }


    public @NotNull Class<?> getSearchClass() {
        return searchClass;
    }

    public @NotNull EasyTableMatch getTableMatch() {
        return tableMatch;
    }
}
