package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2023/12/16 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Fetcher extends SQLSelectAsExpression {
    Fetcher fetch(SQLSelectAsExpression... selectAsExpressions);
    Fetcher fetch(SQLSelectExpression... selectExpressions);
    default TableAvailable getTable() {
        return null;
    }


    default String getValue() {
        return null;
    }

    default EntitySQLContext getEntitySQLContext() {
        return null;
    }
}
