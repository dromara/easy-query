package com.easy.query.core.proxy.grouping;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.flat.casewhen.FlatElementCaseWhenEntityBuilder;
import com.easy.query.core.proxy.extension.functions.executor.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.NumberTypeExpressionImpl;

/**
 * create time 2025/3/5 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class FlatElementSQLAnyQueryable {
    private final SQLPredicateExpression predicate;
    private final EntitySQLContext entitySQLContext;

    public FlatElementSQLAnyQueryable(EntitySQLContext entitySQLContext, SQLPredicateExpression predicate) {
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
