package com.easy.query.core.proxy.grouping;

import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.flat.casewhen.FlatElementCaseWhenEntityBuilder;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.math.BigDecimal;

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
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {

        PropTypeColumn<?> preColumn = new FlatElementCaseWhenEntityBuilder(this.entitySQLContext).caseWhen(predicate).then(1).elseEnd(null, Long.class);
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.entitySQLContext, preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, preColumn);
            }).distinct(false);
        }, Long.class);
    }
}
