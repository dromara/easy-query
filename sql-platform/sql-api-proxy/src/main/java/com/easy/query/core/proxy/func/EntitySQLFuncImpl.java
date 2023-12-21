package com.easy.query.core.proxy.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/21 09:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntitySQLFuncImpl implements EntitySQLFunc{

    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;

    public EntitySQLFuncImpl(EntitySQLContext entitySQLContext, TableAvailable table){

        this.entitySQLContext = entitySQLContext;
        this.table = table;
    }
    @Override
    public ColumnFuncComparableExpression<String> subString(DSLSQLFunctionAvailable dslsqlFunctionAvailable, int begin, int length) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.entitySQLContext,this.table,null, fx->{
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(fx);
            return fx.subString(sqlFunction,begin,length);
        },String.class);
    }
}
