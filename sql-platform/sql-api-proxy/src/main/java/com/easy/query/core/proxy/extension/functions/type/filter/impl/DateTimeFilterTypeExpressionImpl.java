package com.easy.query.core.proxy.extension.functions.type.filter.impl;

import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.filter.DateTimeFilterTypeExpression;

/**
 * create time 2023/12/2 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class DateTimeFilterTypeExpressionImpl<TProperty> extends AbstractAggregateFilterExpression<TProperty>
        implements DateTimeFilterTypeExpression<TProperty> {


    public DateTimeFilterTypeExpressionImpl(EntitySQLContext entitySQLContext, PropTypeColumn<?> self, TableAvailable table, String property, SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        super(entitySQLContext,self,table,property,func,propType);
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
