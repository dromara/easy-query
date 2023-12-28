package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLFunctionExpressionUtil;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.ColumnFunctionComparableChainExpression;

import java.util.function.Function;

/**
 * create time 2023/12/2 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnFunctionComparableExpressionImpl<TProperty> implements ColumnFunctionComparableChainExpression<TProperty> {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String property;
    private final Function<SQLFunc, SQLFunction> func;
    private Class<?> propType;

    public SQLColumnFunctionComparableExpressionImpl(EntitySQLContext entitySQLContext,TableAvailable table, String property, Function<SQLFunc, SQLFunction> func) {
        this(entitySQLContext,table,property,func,Object.class);
    }
    public SQLColumnFunctionComparableExpressionImpl(EntitySQLContext entitySQLContext,TableAvailable table, String property, Function<SQLFunc, SQLFunction> func,Class<?> propType) {
        this.entitySQLContext = entitySQLContext;

        this.table = table;
        this.property = property;
        this.func = func;
        this.propType=propType;
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
    public void asc(boolean condition) {
        if(condition){
            getEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
                SQLFunctionExpressionUtil.accept(s,getTable(),func,true);
            }));
        }

    }

    @Override
    public void desc(boolean condition) {
        if(condition){
              getEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
                SQLFunctionExpressionUtil.accept(s,getTable(),func,false);
            }));
        }
    }

    @Override
    public Function<SQLFunc, SQLFunction> func() {
        return this.func;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    @Override
    public Class<?> getPropertyType() {
        return propType;
    }

    @Override
    public <TR> void _setPropertyType(Class<TR> clazz) {
        this.propType=clazz;
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
