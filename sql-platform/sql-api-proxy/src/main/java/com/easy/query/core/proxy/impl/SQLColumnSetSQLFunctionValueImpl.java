package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/27 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnSetSQLFunctionValueImpl implements SQLColumnSetExpression {
    private final TableAvailable table;
    private final String property;
    private final DSLSQLFunctionAvailable sqlFunctionAvailable;

    public SQLColumnSetSQLFunctionValueImpl(TableAvailable table, String property, DSLSQLFunctionAvailable sqlFunctionAvailable){
        this.table = table;
        this.property = property;
        this.sqlFunctionAvailable = sqlFunctionAvailable;
    }

    @Override
    public void accept(Setter s) {
        Function<SQLFunc, SQLFunction> func = sqlFunctionAvailable.func();
        SQLFunc fx = s.getRuntimeContext().fx();
        s.setFunc(table, property, func.apply(fx));
    }

    @Override
    public void accept(Selector s) {
        Function<SQLFunc, SQLFunction> func = sqlFunctionAvailable.func();
        SQLFunc fx = s.getRuntimeContext().fx();
        s.columnFunc(sqlFunctionAvailable.getTable(),func.apply(fx),property);
    }

    @Override
    public void accept(AsSelector s) {
        Function<SQLFunc, SQLFunction> func = sqlFunctionAvailable.func();
        SQLFunc fx = s.getRuntimeContext().fx();
        s.columnFunc(sqlFunctionAvailable.getTable(),func.apply(fx),property);
    }
}
