package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;

/**
 * create time 2025/9/8 16:45
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SmartPredicateAnonymousExpressionBuilderProvider {
    void process(AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder, PredicateSegment predicateSegment);
}
