package com.easy.query.core.func.column.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnLazyFunctionExpression;

import java.util.function.Function;

/**
 * create time 2024/6/13 12:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnLazyFunctionExpressionImpl implements ColumnLazyFunctionExpression {
    private final TableAvailable table;
    private final Function<SQLFunc, SQLFunction> sqlFuncSQLFunctionCreator;

    public ColumnLazyFunctionExpressionImpl(TableAvailable table, Function<SQLFunc, SQLFunction> sqlFuncSQLFunctionCreator){

        this.table = table;
        this.sqlFuncSQLFunctionCreator = sqlFuncSQLFunctionCreator;
    }
    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Function<SQLFunc, SQLFunction> getSQLFunctionCreator() {
        return sqlFuncSQLFunctionCreator;
    }
}
