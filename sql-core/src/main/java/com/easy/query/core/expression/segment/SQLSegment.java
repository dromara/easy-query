package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/14 23:07
 */
public interface SQLSegment {
    String toSQL(ToSQLContext toSQLContext);
}
