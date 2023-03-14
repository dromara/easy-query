package com.easy.query.core.expression.segment;

import com.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: SqlEntitySegment.java
 * @Description: 文件说明
 * @Date: 2023/3/4 23:48
 * @Created by xuejiaming
 */
public interface SqlEntityAliasSegment extends SqlEntitySegment {
    String getAlias();
}
