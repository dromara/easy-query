package com.easy.query.core.proxy.extension.functions.executor.impl;

import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.SQLFunctionExpressionUtil;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnObjectFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberFilterChainExpression;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;

import java.util.function.Function;

/**
 * create time 2023/12/2 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFunctionCompareComparableNumberFilterChainExpressionImpl<TProperty> implements ColumnFunctionCompareComparableNumberFilterChainExpression<TProperty> {
    private final EntitySQLContext entitySQLContext;
    private ColumnObjectFunctionAvailable<?, ?> self;
    private final TableAvailable table;
    private final String property;
    private final SQLFuncExpression2<ColumnObjectFunctionAvailable<?, ?>, SQLFunc, SQLFunction> func;
    private Class<?> propType;

    public ColumnFunctionCompareComparableNumberFilterChainExpressionImpl(EntitySQLContext entitySQLContext, ColumnObjectFunctionAvailable<?, ?> self, TableAvailable table, String property, SQLFuncExpression2<ColumnObjectFunctionAvailable<?, ?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        this.entitySQLContext = entitySQLContext;
        this.self = self;

        this.table = table;
        this.property = property;
        this.func = func;
        this.propType = propType;
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
        SQLFunctionExpressionUtil.accept(s, getTable(), fx -> func.apply(getSelf(), fx));
    }

    @Override
    public void accept(AsSelector s) {
        SQLFunctionExpressionUtil.accept(s, getTable(), fx -> func.apply(getSelf(), fx));
    }

    @Override
    public void accept(GroupSelector s) {
        SQLFunctionExpressionUtil.accept(s, getTable(), fx -> func.apply(getSelf(), fx));
    }

    @Override
    public void accept(OrderSelector s) {
        SQLFunctionExpressionUtil.accept(s, getTable(), fx -> func.apply(getSelf(), fx));
    }

    //    @Override
//    public void asc(boolean condition) {
//        if(condition){
//            getEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
//                SQLFunctionExpressionUtil.accept(s,getTable(),func,true);
//            }));
//        }
//
//    }
    @Override
    public void asc(boolean condition, OrderByModeEnum nullsModeEnum) {
        if (condition) {

            getEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
                s.setAsc(true);
                SQLFunc fx = getEntitySQLContext().getRuntimeContext().fx();
                SQLFunction sqlFunction = func.apply(getSelf(), fx);
                if (nullsModeEnum != null) {
                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(sqlFunction, true, nullsModeEnum);
                    s.func(this.getTable(), orderByNullsModeFunction, false);
                } else {
                    s.func(this.getTable(), sqlFunction, true);
                }
            }));
        }
    }

    @Override
    public void desc(boolean condition, OrderByModeEnum nullsModeEnum) {
        if (condition) {

            getEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
                s.setAsc(false);
                SQLFunc fx = getEntitySQLContext().getRuntimeContext().fx();
                SQLFunction sqlFunction = func.apply(getSelf(),fx);
                if (nullsModeEnum != null) {
                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(sqlFunction, false, nullsModeEnum);
                    s.func(this.getTable(), orderByNullsModeFunction, false);
                } else {
                    s.func(this.getTable(), sqlFunction, true);
                }
            }));
        }
    }

    @Override
    public Function<SQLFunc, SQLFunction> func() {
        return fx -> {
            SQLFunction sqlFunction = func.apply(getSelf(), fx);
            return sqlFunction;
        };
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
        this.propType = clazz;
    }

    private ColumnObjectFunctionAvailable<?, ?> getSelf(){
        return this.self;
    }

    @Override
    public void _toFilter(SQLActionExpression predicate) {
        this.self = new CaseWhenEntityBuilder(this.getEntitySQLContext()).caseWhen(predicate).then(getSelf()).elseEnd(null, getPropertyType());
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
