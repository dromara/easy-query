package com.easy.query.core.sharding.merge.executor.common;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;

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
    private final CommandTypeEnum commandType;

    public SqlUnit(String sql, List<SQLParameter> parameters, CommandTypeEnum commandType){
        this(sql,parameters,null,commandType);
    }
    public SqlUnit(String sql, List<SQLParameter> parameters, List<Object> entities, CommandTypeEnum commandType){
        this(sql,parameters,entities,false,commandType);
    }
    public SqlUnit(String sql, List<SQLParameter> parameters, List<Object> entities, boolean fillAutoIncrement, CommandTypeEnum commandType) {
        this.sql = sql;
        this.parameters = parameters;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
        this.commandType = commandType;
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

    public CommandTypeEnum getCommandType() {
        return commandType;
    }
}
