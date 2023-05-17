package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.expression.InsertSQLExpression;

/**
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 * @author xuejiaming
 */
public interface EntityInsertExpressionBuilder extends EntityExpressionBuilder,EntityToExpressionBuilder {
    SQLBuilderSegment getColumns();
    @Override
    default InsertSQLExpression toExpression(){
        return toExpression(null);
    }
    @Override
    InsertSQLExpression toExpression(Object entity);

}