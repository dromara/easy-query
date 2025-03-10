package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;

/**
 * create time 2023/6/18 11:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtNavigateIncludeImpl<T1> implements SQLKtNavigateInclude<T1> {
    private final NavigateInclude navigateInclude;

    public SQLKtNavigateIncludeImpl(NavigateInclude navigateInclude){

        this.navigateInclude = navigateInclude;
    }
    @Override
    public NavigateInclude getNavigateInclude() {
        return navigateInclude;
    }
}
