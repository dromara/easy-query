package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.PropValueConvertColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/27 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnSetPropColumnImpl implements SQLColumnSetExpression {
    private final TableAvailable table;
    private final String property;
    private final PropTypeColumn<?> val;

    public SQLColumnSetPropColumnImpl(TableAvailable table, String property, PropTypeColumn<?> val) {
        this.table = table;
        this.property = property;
        this.val = val;
    }

    @Override
    public void accept(Setter s) {
        if (val instanceof SQLColumn) {
            SQLColumn<?, ?> sqlColumn = (SQLColumn<?, ?>) val;
//            if (table != sqlColumn.getTable()) {
//                throw new UnsupportedOperationException();
//            }
            s.setWithColumn(true,table, property,sqlColumn.getTable(), sqlColumn.getValue());
        } else if (val instanceof DSLSQLFunctionAvailable) {
            DSLSQLFunctionAvailable sqlFunctionAvailable = (DSLSQLFunctionAvailable) val;
            Function<SQLFunc, SQLFunction> func = sqlFunctionAvailable.func();
            SQLFunc fx = s.getRuntimeContext().fx();
            s.setFunc(table, property, func.apply(fx));
        }else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void accept(Selector s) {
        val.accept(s);
    }

    @Override
    public void accept(AsSelector s) {
        if (property != null) {
            val.as(property).accept(s);
        } else {
            val.accept(s);
        }
    }
}
