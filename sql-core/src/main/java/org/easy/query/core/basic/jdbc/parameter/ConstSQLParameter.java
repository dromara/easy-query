package org.easy.query.core.basic.jdbc.parameter;

import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ConstSqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 * @Created by xuejiaming
 */
public final class ConstSQLParameter implements SQLParameter {
    private final SqlTableInfo table;
    private final String propertyName;
    private final Object val;

    public ConstSQLParameter(SqlTableInfo table, String propertyName,Object val){
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
    }

    @Override
    public SqlTableInfo getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Object getValue() {
        return val;
    }
}
