package com.easy.query.test.provider;

import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/10/28 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLProviderImpl<T> implements MySQLProvider<T> {

    private final WherePredicate<T> wherePredicate;

    public MySQLProviderImpl(WherePredicate<T> wherePredicate){

        this.wherePredicate = wherePredicate;
    }

    @Override
    public WherePredicate<T> getWherePredicate() {
        return wherePredicate;
    }
}
