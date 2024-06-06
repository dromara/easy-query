package com.easy.query.core.basic.api.internal;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

/**
 * create time 2024/6/6 20:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface BehaviorConfigure<TChain> {
    /**
     * 默认的行为{@link EasyBehavior#DEFAULT_BEHAVIOR }
     * @param configure
     * @return
     */
    TChain behaviorConfigure(SQLExpression1<EasyBehavior> configure);
}
