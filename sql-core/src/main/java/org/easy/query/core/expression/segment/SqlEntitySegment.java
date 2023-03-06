package org.easy.query.core.expression.segment;

import org.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: SqlEntitySegment.java
 * @Description: 文件说明
 * @Date: 2023/3/4 23:48
 * @Created by xuejiaming
 */
public interface SqlEntitySegment extends SqlSegment {
    SqlEntityTableExpression getTable();
    String getPropertyName();
    String getAlias();
}
