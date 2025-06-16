package com.easy.query.core.expression.sql.include;

import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateExpression;

import java.util.List;

/**
 * create time 2023/7/21 10:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeParserEngine {
    <TR> IncludeParserResult process(ExpressionContext expressionContext, EntityMetadata entityMetadata, List<TR> result, IncludeNavigateExpression includeExpression);
}
