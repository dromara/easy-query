package com.easy.query.core.proxy.extension.functions.type.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.PartitionByTypeExpression;

import java.util.function.Function;

/**
 * create time 2023/12/2 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartitionByTypeExpressionImpl<TProperty> extends AnyTypeExpressionImpl<TProperty> implements PartitionByTypeExpression<TProperty> {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String property;
    private final Function<SQLFunc, PartitionBySQLFunction> func;
    private Class<?> propType;


    public PartitionByTypeExpressionImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, PartitionBySQLFunction> func, Class<?> propType) {
        super(entitySQLContext,table,property,f->func.apply(f),propType);
        this.entitySQLContext = entitySQLContext;

        this.table = table;
        this.property = property;
        this.func = func;
        this.propType = propType;
    }

    @Override
    public <TProperty1> PartitionByTypeExpression<TProperty> orderBy(boolean condition, PropTypeColumn<TProperty1> propTypeColumn) {
        if(condition){
            return new PartitionByTypeExpressionImpl<>(entitySQLContext,table,property, f->{
                PartitionBySQLFunction sqlFunction = func.apply(f);
                SQLFunction orderFunction = f.anySQLFunction("{0} ASC", c -> {
                    PropTypeColumn.columnFuncSelector(c, propTypeColumn);
                });
                sqlFunction.addOrder(orderFunction);
                return sqlFunction;
            },propType);
        }
        return this;
    }

    @Override
    public <TProperty1> PartitionByTypeExpression<TProperty> orderByDescending(boolean condition, PropTypeColumn<TProperty1> propTypeColumn) {

        if(condition){
            return new PartitionByTypeExpressionImpl<>(entitySQLContext,table,property, f->{
                PartitionBySQLFunction sqlFunction = func.apply(f);
                SQLFunction orderFunction = f.anySQLFunction("{0} DESC", c -> {
                    PropTypeColumn.columnFuncSelector(c, propTypeColumn);
                });
                sqlFunction.addOrder(orderFunction);
                return sqlFunction;
            },propType);
        }
        return this;
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
