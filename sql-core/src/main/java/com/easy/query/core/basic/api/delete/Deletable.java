package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.internal.ContextConfigure;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.SQLExecuteExpectRows;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * @author xuejiaming
 * @FileName: Deletable.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:19
 */
public interface Deletable<T, TChain> extends SQLExecuteExpectRows,
        Interceptable<TChain>,
        LogicDeletable<TChain>,
        ContextConfigure<TChain>,
        TableReNameable<TChain> {
    ExpressionContext getExpressionContext();

    EntityDeleteExpressionBuilder getDeleteExpressionBuilder();
    /**
     * 语句转成sql
     *
     * @return
     */
    default String toSQL() {
        return toSQL(DefaultToSQLContext.defaultToSQLContext(getExpressionContext().getTableContext()));
    }

    String toSQL(ToSQLContext toSQLContext);

    /**
     * 传入生成sql的上下文用来获取生成sql后的表达式内部的参数
     * @return 包含sql和sql结果比如参数
     */

    default ToSQLResult toSQLResult() {
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(getExpressionContext().getTableContext());
        String sql = toSQL(toSQLContext);
        return new ToSQLResult(sql,toSQLContext);
    }
    /**
     * 是否允许删除命令
     *
     * @param allow
     * @return
     */
    TChain allowDeleteStatement(boolean allow);
}
