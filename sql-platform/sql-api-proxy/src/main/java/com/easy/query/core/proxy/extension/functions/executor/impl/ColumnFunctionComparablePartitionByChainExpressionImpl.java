package com.easy.query.core.proxy.extension.functions.executor.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLFunctionExpressionUtil;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparablePartitionByChainExpression;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/2 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFunctionComparablePartitionByChainExpressionImpl<TProperty> extends ColumnFunctionComparableAnyChainExpressionImpl<TProperty> implements ColumnFunctionComparablePartitionByChainExpression<TProperty> {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String property;
    private final Function<SQLFunc, PartitionBySQLFunction> func;
    private Class<?> propType;


    public ColumnFunctionComparablePartitionByChainExpressionImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, PartitionBySQLFunction> func, Class<?> propType) {
        super(entitySQLContext,table,property,f->func.apply(f),propType);
        this.entitySQLContext = entitySQLContext;

        this.table = table;
        this.property = property;
        this.func = func;
        this.propType = propType;
    }

    @Override
    public <TProperty1> ColumnFunctionComparablePartitionByChainExpression<TProperty> orderBy(PropTypeColumn<TProperty1> propTypeColumn) {
        return new ColumnFunctionComparablePartitionByChainExpressionImpl<>(entitySQLContext,table,property,f->{
            PartitionBySQLFunction sqlFunction = func.apply(f);
            SQLFunction orderFunction = f.anySQLFunction("{0} ASC", c -> {
                PropTypeColumn.columnFuncSelector(c, propTypeColumn);
            });
            sqlFunction.addOrder(orderFunction);
            return sqlFunction;
        },propType);
    }

    @Override
    public <TProperty1> ColumnFunctionComparablePartitionByChainExpression<TProperty> orderByDescending(PropTypeColumn<TProperty1> propTypeColumn) {

        return new ColumnFunctionComparablePartitionByChainExpressionImpl<>(entitySQLContext,table,property,f->{
            PartitionBySQLFunction sqlFunction = func.apply(f);
            SQLFunction orderFunction = f.anySQLFunction("{0} DESC", c -> {
                PropTypeColumn.columnFuncSelector(c, propTypeColumn);
            });
            sqlFunction.addOrder(orderFunction);
            return sqlFunction;
        },propType);
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
