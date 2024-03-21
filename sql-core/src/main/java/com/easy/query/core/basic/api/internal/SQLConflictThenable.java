package com.easy.query.core.basic.api.internal;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;

import java.util.Collection;
import java.util.Collections;

/**
 * create time 2024/3/21 13:16
 * 出现争议后的处理
 *
 * @author xuejiaming
 */
public interface SQLConflictThenable<T, TChain> {

    /**
     * 存在有争议的情况下处理
     * @param updateSetSelector 选择需要更新的列 o->{}表示不更新
     * @return
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 不指定唯一约束时如果当前表没有主键报错
     */
   default TChain onConflictThen(@Nullable SQLExpression1<ColumnOnlySelector<T>> updateSetSelector){
       return onConflictThen(updateSetSelector,(String) null);
   }
    /**
     * 存在有争议的情况下处理
     * @param updateSetSelector 选择需要更新的列 null || o->{}表示不更新
     * @param constraintProperty null表示不指定使用主键
     * @return
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 不指定唯一约束时如果当前表没有主键报错
     */
    default TChain onConflictThen(@Nullable SQLExpression1<ColumnOnlySelector<T>> updateSetSelector,@Nullable String constraintProperty){
       Collection<String> constraintProperties = constraintProperty==null?null: Collections.singletonList(constraintProperty);
        return onConflictThen(updateSetSelector,constraintProperties);
    }
    /**
     * 存在有争议的情况下处理
     * @param updateSetSelector 选择需要更新的列 null || o->{}表示不更新
     * @param constraintProperties null表示不指定使用主键
     * @return
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 不指定唯一约束时如果当前表没有主键报错
     */
    TChain onConflictThen(@Nullable SQLExpression1<ColumnOnlySelector<T>> updateSetSelector,@Nullable Collection<String> constraintProperties);

}
