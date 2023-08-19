package com.easy.query.core.basic.api.internal;

import com.easy.query.core.expression.builder.core.ConditionAccepter;

/**
 * create time 2023/8/19 15:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ConditionConfigurable<TChain> {
    TChain conditionConfigure(ConditionAccepter conditionAccepter);
}
