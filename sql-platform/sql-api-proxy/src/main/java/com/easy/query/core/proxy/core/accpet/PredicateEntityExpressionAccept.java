package com.easy.query.core.proxy.core.accpet;

import com.easy.query.core.expression.builder.Filter;

/**
 * create time 2023/12/8 17:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PredicateEntityExpressionAccept extends EntityExpressionAccept {
    Filter getFilter();
    void nextOr(boolean or);
}
