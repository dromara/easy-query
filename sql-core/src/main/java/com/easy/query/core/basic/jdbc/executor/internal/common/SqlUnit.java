package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.List;

/**
 * create time 2023/4/20 09:19
 * sql执行单元
 *
 * @author xuejiaming
 */
public class SqlUnit {

    private final String sql;
    private final List<SQLParameter> parameters;
    private final List<Object> entities;
    private final boolean fillAutoIncrement;

    public SqlUnit(String sql, List<SQLParameter> parameters){
        this(sql,parameters,null);
    }
    public SqlUnit(String sql, List<SQLParameter> parameters, List<Object> entities){
        this(sql,parameters,entities,false);
    }
    public SqlUnit(String sql, List<SQLParameter> parameters, List<Object> entities, boolean fillAutoIncrement) {
        this.sql = sql;
        this.parameters = parameters;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
    }

    public String getSql() {
        return sql;
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }

    public List<Object> getEntities() {
        return entities;
    }

    public boolean isFillAutoIncrement() {
        return fillAutoIncrement;
    }
}
