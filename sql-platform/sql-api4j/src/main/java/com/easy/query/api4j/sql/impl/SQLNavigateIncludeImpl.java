package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;

/**
 * create time 2023/6/18 11:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNavigateIncludeImpl<T1> implements SQLNavigateInclude<T1> {
    private final NavigateInclude<T1> navigateInclude;

    public SQLNavigateIncludeImpl(NavigateInclude<T1> navigateInclude){

        this.navigateInclude = navigateInclude;
    }
    @Override
    public NavigateInclude<T1> getNavigateInclude() {
        return navigateInclude;
    }
}
