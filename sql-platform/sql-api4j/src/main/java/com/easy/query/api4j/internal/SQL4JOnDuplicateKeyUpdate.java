package com.easy.query.api4j.internal;

import com.easy.query.api4j.sql.SQLColumnSetSelector;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQL4JOnDuplicateKeyUpdate<T, TChain> {
    TChain onConflictDoUpdate();

    /**
     * set all columns without key and constraintProperty
     * @param constraintProperty ON CONFLICT (constraintProperty) DO UPDATE
     * @return
     */
    TChain onConflictDoUpdate(Property<T,?> constraintProperty);

    /**
     * set setColumnSelector columns without key and constraintProperty
     * @param constraintProperty ON CONFLICT (constraintProperty) DO UPDATE
     * @param setColumnSelector selector columns
     * @return
     */
    TChain onConflictDoUpdate(Property<T,?> constraintProperty, SQLExpression1<SQLColumnSetSelector<T>> setColumnSelector);

    TChain onDuplicateKeyUpdate();

    /**
     * set setColumnSelector columns without key
     * @param setColumnSelector
     * @return
     */
    TChain onDuplicateKeyUpdate(SQLExpression1<SQLColumnSetSelector<T>> setColumnSelector);
}
