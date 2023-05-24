package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 * @author xuejiaming
 */
public final class ConstLikeSQLParameter implements ConstSQLParameter, SQLLikeParameter {
    private final EasyConstSQLParameter constSQLParameter;

    public ConstLikeSQLParameter(EasyConstSQLParameter constSQLParameter) {
        this.constSQLParameter = constSQLParameter;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return constSQLParameter.getTableOrNull();
    }

    @Override
    public String getPropertyNameOrNull() {
        return constSQLParameter.getPropertyNameOrNull();
    }

    @Override
    public Object getValue() {
        return constSQLParameter.getValue();
    }
}
