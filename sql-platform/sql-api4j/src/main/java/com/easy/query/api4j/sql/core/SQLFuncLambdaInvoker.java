package com.easy.query.api4j.sql.core;

import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContext;

/**
 * create time 2023/10/5 12:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFuncLambdaInvoker<TEntity> {
    String sqlSegment();
    void contextConsume(SQLNativeLambdaExpressionContext<TEntity> context);
}
