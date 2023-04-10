package com.easy.query.core.expression.executor.query;

import com.easy.query.core.expression.sql.EntityQueryExpression;

import java.util.List;

/**
 * create time 2023/4/9 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyQueryExecutor {
    <TR> List<TR> execute(EntityQueryExpression entityQueryExpression);
}
