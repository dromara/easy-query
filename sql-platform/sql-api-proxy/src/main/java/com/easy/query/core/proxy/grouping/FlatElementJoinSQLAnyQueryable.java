package com.easy.query.core.proxy.grouping;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.flat.casewhen.FlatElementCaseWhenEntityBuilder;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;

/**
 * create time 2025/3/5 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class FlatElementJoinSQLAnyQueryable {
    private final SQLAggregatePredicateExpression predicate;
    private final EntitySQLContext entitySQLContext;

    public FlatElementJoinSQLAnyQueryable(EntitySQLContext entitySQLContext, SQLAggregatePredicateExpression predicate) {
        this.entitySQLContext = entitySQLContext;
        this.predicate = predicate;
    }
    public NumberTypeExpression<Long> count() {

        PropTypeColumn<?> preColumn = new FlatElementCaseWhenEntityBuilder(this.entitySQLContext).caseWhen(predicate).then(1).elseEnd(null, Long.class);
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, preColumn);
            }).distinct(false);
        }, Long.class);
    }
}
