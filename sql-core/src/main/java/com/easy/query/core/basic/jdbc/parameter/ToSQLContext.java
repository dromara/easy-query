package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;

import java.util.List;

/**
 * create time 2023/4/23 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ToSQLContext {
    /**
     * 表达式调用次数
     *
     * @return
     */
    int expressionInvokeCountGetIncrement();

    int currentInvokeCount();

    void addParameter(SQLParameter sqlParameter);

    List<SQLParameter> getParameters();

    SQLRewriteUnit getSQLRewriteUnit();
}
