package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.sql.expression.SQLExpression;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnObjectFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberFilterChainExpressionImpl;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableNumberFilterChainExpression<T> extends ColumnFunctionCompareComparableNumberChainExpression<T> {
    default ColumnFunctionCompareComparableNumberChainExpression<T> filter(SQLActionExpression predicate) {
        this._toFilter(predicate);
        return this;
    }

    void _toFilter(SQLActionExpression predicate);

}
