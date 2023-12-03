package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.extension.ColumnFuncComparable;

import java.util.function.Function;

/**
 * create time 2023/12/2 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnFunctionComparableImpl<TProperty> implements ColumnFuncComparable<TProperty> {
    private final TableAvailable table;
    private final String property;
    private final Function<SQLFunc, SQLFunction> func;

    public SQLColumnFunctionComparableImpl(TableAvailable table, String property, Function<SQLFunc, SQLFunction> func) {

        this.table = table;
        this.property = property;
        this.func = func;
    }

    @Override
    public String value() {
        return property;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public void accept(Selector f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(AsSelector f) {
        f.columnCount(this.table, this.property);
    }

    @Override
    public Function<SQLFunc, SQLFunction> func() {
        return this.func;
    }

}
//        if(condition){
//            return new SQLPredicateImpl(f->{
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.eq(this.table,func.apply(fx),val);
//            });
//        }
//        return SQLPredicate.empty;
//    }
//}
