package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.expression.EasyInsertSQLExpression;

/**
 * @FileName: SqlEntityDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 * @author xuejiaming
 */
public interface EntityInsertExpressionBuilder extends EntityExpressionBuilder,EntityToExpressionBuilder {
    SQLBuilderSegment getColumns();
    @Override
    default EasyInsertSQLExpression toExpression(){
        return toExpression(null);
    }
    @Override
    EasyInsertSQLExpression toExpression(Object entity);

}