package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 * @author xuejiaming
 */
public final class ConstLikeSQLParameter implements ConstSQLParameter, SQLLikeParameter {
    private final SQLParameter sqlParameter;

    public ConstLikeSQLParameter(SQLParameter sqlParameter) {
        this.sqlParameter = sqlParameter;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return sqlParameter.getTableOrNull();
    }

    @Override
    public String getPropertyNameOrNull() {
        return sqlParameter.getPropertyNameOrNull();
    }

    @Override
    public Object getValue() {
        return sqlParameter.getValue();
    }
}
