package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.expression.EntityInsertSQLExpression;

import java.util.Collection;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/3/4 16:30
 */
public interface EntityInsertExpressionBuilder extends EntityExpressionBuilder, EntityToExpressionBuilder,EntityColumnConfigurerExpressionBuilder {
    SQLBuilderSegment getColumns();
    SQLBuilderSegment getDuplicateKeyUpdateColumns();
    Collection<String> getDuplicateKeys();
    void addDuplicateKey(String duplicateKey);
    @Override
    default EntityInsertSQLExpression toExpression() {
        throw new UnsupportedOperationException();
//        return toExpression(null);
    }

    @Override
    EntityInsertSQLExpression toExpression(Object entity);

    @Override
    EntityInsertExpressionBuilder cloneEntityExpressionBuilder();
}