package com.easy.query.core.basic.api.internal;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
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
    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onConflictDoUpdate();


    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
   default TChain onConflictDoUpdate(String constraintProperty){
       return onConflictDoUpdate(Collections.singletonList(constraintProperty));
   }

    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onConflictDoUpdate(Collection<String> constraintProperties);
    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
   default TChain onConflictDoUpdate(String constraintProperty, SQLActionExpression1<ColumnOnlySelector<T>> setColumnSelector){
       return onConflictDoUpdate(Collections.singletonList(constraintProperty),setColumnSelector);
   }
    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onConflictDoUpdate(Collection<String> constraintProperties, SQLActionExpression1<ColumnOnlySelector<T>> setColumnSelector);
    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onConflictDoUpdate(SQLActionExpression1<ColumnOnlySelector<T>> setColumnSelector);

    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onDuplicateKeyUpdate();

    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onDuplicateKeyUpdate(SQLActionExpression1<ColumnOnlySelector<T>> setColumnSelector);
}
