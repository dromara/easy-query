package com.easy.query.core.basic.api.internal;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

/**
 * create time 2024/6/6 20:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ContextConfigure<TChain> {
    TChain configure(SQLExpression1<ContextConfigurer> configurer);
}
