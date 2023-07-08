package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.expression.EntityInsertSQLExpression;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 */
public interface EntityInsertExpressionBuilder extends EntityExpressionBuilder, EntityToExpressionBuilder {
    SQLBuilderSegment getColumns();
    SQLBuilderSegment getDuplicateKeyUpdateColumns();
    String getDuplicateKey();
    void setDuplicateKey(String duplicateKey);

    @Override
    default EntityInsertSQLExpression toExpression() {
        return toExpression(null);
    }

    @Override
    EntityInsertSQLExpression toExpression(Object entity);

    @Override
    EntityInsertExpressionBuilder cloneEntityExpressionBuilder();
}