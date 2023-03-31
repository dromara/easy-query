package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.sql.EntityTableExpression;

/**
 * @FileName: SqlEntitySegment.java
 * @Description: 文件说明
 * @Date: 2023/3/4 23:48
 * @author xuejiaming
 */
public interface SqlEntitySegment extends SqlSegment {
    EntityTableExpression getTable();
    String getPropertyName();
}
