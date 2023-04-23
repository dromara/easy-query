package com.easy.query.core.basic.jdbc.parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/23 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSqlParameterCollector implements SqlParameterCollector{
    private final List<SQLParameter> parameters;
    public DefaultSqlParameterCollector(){
        this(10);
    }
    public DefaultSqlParameterCollector(int initialCapacity){
        parameters=new ArrayList<>(initialCapacity);
    }
    @Override
    public void addParameter(SQLParameter sqlParameter) {
        parameters.add(sqlParameter);
    }

    @Override
    public List<SQLParameter> getParameters() {
        return parameters;
    }

    public static SqlParameterCollector defaultCollector(){
        return defaultCollector(10);
    }
    public static SqlParameterCollector defaultCollector(int initialCapacity){
        return new DefaultSqlParameterCollector();
    }
}
