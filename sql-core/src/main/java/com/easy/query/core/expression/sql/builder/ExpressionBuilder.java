package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.sql.expression.EasySQLExpression;

/**
 * @FileName: TableSegment.java
 * @Description:
 * @Date: 2023/3/3 17:15
 * @author xuejiaming
 */
public interface ExpressionBuilder {

    EasySQLExpression toExpression();
}
