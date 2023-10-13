package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.segment.SQLSegment;

import java.util.Objects;

/**
 * create time 2023/10/13 09:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSegmentParamExpressionImpl implements SQLSegmentParamExpression{
    private final SQLSegment sqlSegment;

    public SQLSegmentParamExpressionImpl(SQLSegment sqlSegment){
        Objects.requireNonNull(sqlSegment,"sqlSegment can not be null");
        this.sqlSegment = sqlSegment;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return sqlSegment.toSQL(toSQLContext);
    }
}
