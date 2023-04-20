package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.sql.EntityQueryExpression;

import java.util.List;

/**
 * create time 2023/4/16 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityExpressionExecutor {
    <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, EntityQueryExpression entityQueryExpression);
    <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, String sql, List<SQLParameter> sqlParameters);
}
