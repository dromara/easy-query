package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.enums.UpdateStrategyEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: EasyEmptyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 * @author xuejiaming
 */
public class EasyEmptyEntityUpdatable<T> implements EntityUpdatable<T> {
    @Override
    public EntityUpdatable<T> setColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> setIgnoreColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> whereColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> setUpdateStrategy(boolean condition, UpdateStrategyEnum updateStrategy) {
        return this;
    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public void executeRows(long expectRows, String msg,String code) {
        long rows = executeRows();
        if(rows!=expectRows){
            throw new EasyQueryConcurrentException(msg,code);
        }
    }
}
