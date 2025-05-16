package com.easy.query.api.proxy.extension.upsert;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityConflictThenable<TProxy extends ProxyEntity<TProxy,T>,T, TChain> {

    /**
     * 存在有争议的情况下处理
     * @param updateSetSelector 选择需要更新的列 null || ()->null 表示不更新
     * @return
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 不指定唯一约束时如果当前表没有主键报错
     */
    default TChain onConflictThen(@Nullable SQLFuncExpression1<TProxy,SQLSelectExpression> updateSetSelector){
        return onConflictThen(updateSetSelector,null);
    }

    /**
     * 存在有争议的情况下处理
     * 如果constraintPropertySelector所在的列的值为null那么insert的时候不会生成列需要这是更新策略为ALL_COLUMNS
     <blockquote><pre>
     * {@code
     *  setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS)
     *                    }
     * </pre></blockquote>
     * @param updateSetSelector 选择需要更新的列 null || ()->null表示不更新
     * @param constraintPropertySelector null表示不指定使用主键
     * @return
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 不指定唯一约束时如果当前表没有主键报错
     */
    TChain onConflictThen(@Nullable SQLFuncExpression1<TProxy,SQLSelectExpression> updateSetSelector,@Nullable SQLFuncExpression1<TProxy,SQLSelectExpression> constraintPropertySelector);
}
