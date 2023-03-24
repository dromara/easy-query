package com.easy.query.core.query;

/**
 * @FileName: AnonymousEntityTableSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 21:55
 * @author xuejiaming
 */
public interface AnonymousEntityTableExpression extends SqlEntityTableExpression {
    SqlEntityQueryExpression getSqlEntityQueryExpression();
}
