package com.easy.query.core.basic.api.internal;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;

import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOnDuplicateKeyUpdate<T, TChain> {
    TChain onConflictDoUpdate();

   default TChain onConflictDoUpdate(String constraintProperty){
       return onConflictDoUpdate(Collections.singletonList(constraintProperty));
   }
    TChain onConflictDoUpdate(Collection<String> constraintProperties);
   default TChain onConflictDoUpdate(String constraintProperty, SQLExpression1<ColumnOnlySelector<T>> setColumnSelector){
       return onConflictDoUpdate(Collections.singletonList(constraintProperty),setColumnSelector);
   }
    TChain onConflictDoUpdate(Collection<String> constraintProperties, SQLExpression1<ColumnOnlySelector<T>> setColumnSelector);
    TChain onConflictDoUpdate(SQLExpression1<ColumnOnlySelector<T>> setColumnSelector);

    TChain onDuplicateKeyUpdate();

    TChain onDuplicateKeyUpdate(SQLExpression1<ColumnOnlySelector<T>> setColumnSelector);
}
