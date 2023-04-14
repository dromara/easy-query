package com.easy.query.core.sharding.merge.executor.common;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * create time 2023/4/14 16:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertSqlUnit extends BaseSqlUnit{
    private final List<Object> entities;
    private final List<SQLParameter> parameters;
    private final boolean fillAutoIncrement;

    public InsertSqlUnit(String sql, List<Object> entities, List<SQLParameter> parameters,boolean fillAutoIncrement) {
        super(sql);
        this.entities = entities;
        this.parameters = parameters;
        this.fillAutoIncrement = fillAutoIncrement;
    }

    public List<Object> getEntities() {
        return entities;
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }

    public boolean isFillAutoIncrement() {
        return fillAutoIncrement;
    }
}
