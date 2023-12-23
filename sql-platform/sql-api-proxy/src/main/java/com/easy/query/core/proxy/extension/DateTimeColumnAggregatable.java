package com.easy.query.core.proxy.extension;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DateTimeColumnAggregatable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {

    default ColumnFunctionComparableChainExpression<String> dateTimeFormatV2(String javaFormat) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.dateTimeFormat(this.getValue(), javaFormat);
        }, String.class);
    }
}
