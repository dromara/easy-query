package com.easy.query.core.func.column.impl;

import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.func.column.ColumSQLExpression;

/**
  * create time 2023/10/18 09:44
  * 文件说明
  * @author xuejiaming
  */
public class ColumSQLExpressionImpl implements ColumSQLExpression {
    private final SQLSegment sqlSegment;

    public ColumSQLExpressionImpl(SQLSegment sqlSegment){

        this.sqlSegment = sqlSegment;
    }

    @Override
    public SQLSegment getSQLSegment() {
        return sqlSegment;
    }
}
