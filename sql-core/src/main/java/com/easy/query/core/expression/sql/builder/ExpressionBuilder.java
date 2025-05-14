package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.sql.expression.SQLExpression;

/**
 * @FileName: TableSegment.java
 * @Description:
 * create time 2023/3/3 17:15
 * @author xuejiaming
 */
public interface ExpressionBuilder {

    SQLExpression toExpression();
}
