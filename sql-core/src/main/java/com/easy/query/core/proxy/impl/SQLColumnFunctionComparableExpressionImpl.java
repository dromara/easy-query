package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLFunctionExpressionUtil;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;

import java.util.function.Function;

/**
 * create time 2023/12/2 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnFunctionComparableExpressionImpl<TProperty> implements ColumnFuncComparableExpression<TProperty> {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String property;
    private final Function<SQLFunc, SQLFunction> func;

    public SQLColumnFunctionComparableExpressionImpl(EntitySQLContext entitySQLContext,TableAvailable table, String property, Function<SQLFunc, SQLFunction> func) {
        this.entitySQLContext = entitySQLContext;

        this.table = table;
        this.property = property;
        this.func = func;
    }

    @Override
    public String getValue() {
        return property;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public void accept(Selector s) {
        SQLFunctionExpressionUtil.accept(s,getTable(),func);
    }

    @Override
    public void accept(AsSelector s) {
        SQLFunctionExpressionUtil.accept(s,getTable(),func);
    }

    @Override
    public void accept(GroupSelector s) {
        SQLFunctionExpressionUtil.accept(s,getTable(),func);
    }

    @Override
    public void accept(OrderSelector s) {
        SQLFunctionExpressionUtil.accept(s,getTable(),func);
    }

    @Override
    public SQLOrderByExpression asc(boolean condition) {
        if(condition){
            return  new SQLOrderSelectImpl(s -> {
                SQLFunctionExpressionUtil.accept(s,getTable(),func,true);
            });
        }
        return SQLOrderByExpression.empty;

    }

    @Override
    public SQLOrderByExpression desc(boolean condition) {
        if(condition){
            return  new SQLOrderSelectImpl(s -> {
                SQLFunctionExpressionUtil.accept(s,getTable(),func,false);
            });
        }
        return SQLOrderByExpression.empty;
    }

    @Override
    public Function<SQLFunc, SQLFunction> func() {
        return this.func;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
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
