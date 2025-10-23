package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.ResultSetContext;
import com.easy.query.core.expression.lambda.SQLResultSetFunc;

import java.util.List;

/**
 * create time 2025/10/23 21:05
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ResultSetAble {
    <T> T toResultSet(SQLResultSetFunc<ResultSetContext,T> produce);
}
