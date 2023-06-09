package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import kotlin.reflect.KProperty1;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 */
public class SQLKtColumnAsSelectorImpl<T1, TR> implements SQLKtColumnAsSelector<T1, TR> {
    private final ColumnAsSelector<T1, TR> columnAsSelector;

    public SQLKtColumnAsSelectorImpl(ColumnAsSelector<T1, TR> columnAsSelector) {
        this.columnAsSelector = columnAsSelector;
    }

    @Override
    public ColumnAsSelector<T1, TR> getColumnAsSelector() {
        return columnAsSelector;
    }

}
