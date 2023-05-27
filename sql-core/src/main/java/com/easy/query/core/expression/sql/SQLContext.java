package com.easy.query.core.expression.sql;

import com.easy.query.core.context.QueryRuntimeContext;

/**
 * create time 2023/5/27 08:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLContext {
     TableContext getTableContext();
     QueryRuntimeContext getQueryRuntimeContext();
    /**
     * 设置当前为sharding需要解析
     * 可以对非sharding的表达式进行优化不需要判断
     */
    void useSharding();
    boolean isSharding();
    boolean hasSubQuery();
    void addSubQuery();
}
