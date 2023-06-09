package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 */
public class SQLColumnAsSelectorImpl<T1, TR> implements SQLColumnAsSelector<T1, TR> {
    private final ColumnAsSelector<T1, TR> columnAsSelector;

    public SQLColumnAsSelectorImpl(ColumnAsSelector<T1, TR> columnAsSelector) {
        this.columnAsSelector = columnAsSelector;
    }

    @Override
    public ColumnAsSelector<T1, TR> getColumnAsSelector() {
        return columnAsSelector;
    }

}
