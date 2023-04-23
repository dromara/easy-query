package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;

/**
 * @FileName: SqlEntityDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 * @author xuejiaming
 */
public interface EntityInsertExpressionBuilder extends EntityExpressionBuilder {
    SqlBuilderSegment getColumns();
    @Override
    default EasyInsertSqlExpression toExpression(){
        return toExpression(null);
    }
    EasyInsertSqlExpression toExpression(Object entity);

}