package com.easy.query.core.basic.api.save;

import com.easy.query.core.basic.api.internal.SQLExecuteExpectRows;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2025/9/3 11:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Savable {
    /**
     * 执行命令
     * @return
     */
    void executeCommand();
    void executeCommand2();
}
