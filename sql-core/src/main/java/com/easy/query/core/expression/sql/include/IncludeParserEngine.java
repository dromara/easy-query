package com.easy.query.core.expression.sql.include;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateParams;

import java.util.List;

/**
 * create time 2023/7/21 10:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeParserEngine {
    <TR> IncludeParserResult process(EntityQueryExpressionBuilder mainEntityQueryExpressionBuilder, EntityMetadata entityMetadata, List<TR> result, SQLFuncExpression1<IncludeNavigateParams, ClientQueryable<?>> includeExpression);
}
