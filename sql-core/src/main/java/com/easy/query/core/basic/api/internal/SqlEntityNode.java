package com.easy.query.core.basic.api.internal;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: UpdateEntityNode.java
 * @Description: 文件说明
 * @Date: 2023/3/20 11:21
 * @author xuejiaming
 */
public class SqlEntityNode {

    private final String sql;
    private final List<SQLParameter> sqlParameters;
    private final List<Object> entities;

    public SqlEntityNode(String sql, List<SQLParameter> sqlParameters){
        this(sql,sqlParameters,10);
    }

    public SqlEntityNode(String sql, List<SQLParameter> sqlParameters, int entityCapacity){
        this.sql = sql;
        this.sqlParameters = sqlParameters;
        this.entities=new ArrayList<>(entityCapacity);
    }

    public String getSql() {
        return sql;
    }
    public List<SQLParameter> getSqlParameters() {
        return sqlParameters;
    }

    public List<Object> getEntities() {
        return entities;
    }
}
