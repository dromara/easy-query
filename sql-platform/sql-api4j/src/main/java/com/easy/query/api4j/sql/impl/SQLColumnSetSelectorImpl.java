package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLColumnSetSelector;
import com.easy.query.core.expression.parser.core.base.ColumnUpdateSetSelector;
import com.easy.query.core.expression.parser.core.base.core.SQLColumnPropertyNative;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/16 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnSetSelectorImpl<T1> implements SQLColumnSetSelector<T1> {
    private final ColumnUpdateSetSelector<T1> columnSetSelector;

    public SQLColumnSetSelectorImpl(ColumnUpdateSetSelector<T1> columnSetSelector){

        this.columnSetSelector = columnSetSelector;
    }
    @Override
    public ColumnUpdateSetSelector<T1> getColumnSetSelector() {
        return columnSetSelector;
    }

    @Override
    public <T> SQLColumnPropertyNative<T> getSQLColumnPropertyNative() {
        return EasyObjectUtil.typeCastNullable(columnSetSelector);
    }

    @Override
    public SQLColumnSetSelector<T1> castTChain() {
        return this;
    }
}
