package com.easy.query.core.basic.api.internal;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnUpdateSetSelector;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOnDuplicateKeyUpdate<T, TChain> {
    TChain onConflictDoUpdate();

    TChain onConflictDoUpdate(String constraintProperty);
    TChain onConflictDoUpdate(String constraintProperty, SQLExpression1<ColumnUpdateSetSelector<T>> setColumnSelector);
    TChain onConflictDoUpdate(SQLExpression1<ColumnUpdateSetSelector<T>> setColumnSelector);

    TChain onDuplicateKeyUpdate();

    TChain onDuplicateKeyUpdate(SQLExpression1<ColumnUpdateSetSelector<T>> setColumnSelector);
}
