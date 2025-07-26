package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.segment.SQLSegment;

/**
 * create time 2023/10/13 09:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSegmentParamExpression extends ParamExpression{
    String toSQL(ToSQLContext toSQLContext);
    SQLSegment getSQLSegment();
}
