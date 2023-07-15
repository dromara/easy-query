package com.easy.query.api4kt.internal;

import com.easy.query.api4kt.sql.SQLKtColumnSetSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOnDuplicateKeyUpdate<T, TChain> {
    TChain onConflictDoUpdate();

    TChain onConflictDoUpdate(KProperty1<T,?> constraintProperty);
    TChain onConflictDoUpdate(KProperty1<T,?> constraintProperty, SQLExpression1<SQLKtColumnSetSelector<T>> setColumnSelector);

    TChain onDuplicateKeyUpdate();

    TChain onDuplicateKeyUpdate(SQLExpression1<SQLKtColumnSetSelector<T>> setColumnSelector);
}
