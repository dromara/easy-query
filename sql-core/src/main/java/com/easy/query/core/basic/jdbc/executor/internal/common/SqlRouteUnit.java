package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.basic.jdbc.parameter.DefaultSqlParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;

import java.util.List;

/**
 * create time 2023/4/20 09:19
 * sql执行单元
 *
 * @author xuejiaming
 */
public class SqlRouteUnit {

    private final String sql;
    private final List<SQLParameter> parameters;
    private final List<Object> entities;
    private final boolean fillAutoIncrement;
    private final EasyEntitySqlExpression easyEntitySqlExpression;

    public SqlRouteUnit(String sql, List<SQLParameter> parameters){
        this(sql,parameters,null,false);
    }
    public SqlRouteUnit(String sql, List<SQLParameter> parameters, List<Object> entities, boolean fillAutoIncrement) {
        this.sql = sql;
        this.parameters = parameters;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
        this.easyEntitySqlExpression = null;
    }

    public SqlRouteUnit(EasyEntitySqlExpression easyEntitySqlExpression, List<Object> entities, boolean fillAutoIncrement){
        this.easyEntitySqlExpression = easyEntitySqlExpression;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
        this.sql = null;
        this.parameters = null;
    }

//    public String getSql() {
//        return sql;
//    }
//
//    public List<SQLParameter> getParameters() {
//        return parameters;
//    }
//
//    public List<Object> getEntities() {
//        return entities;
//    }
//
//    public boolean isFillAutoIncrement() {
//        return fillAutoIncrement;
//    }

    public EasyEntitySqlExpression getEasyEntitySqlExpression() {
        return easyEntitySqlExpression;
    }
    public SqlUnit getSqlUnit(){
        if(easyEntitySqlExpression==null){
            return new SqlUnit(sql,parameters,entities,fillAutoIncrement);
        }
        SqlParameterCollector sqlParameterCollector = DefaultSqlParameterCollector.defaultCollector();
        String sql = easyEntitySqlExpression.toSql(sqlParameterCollector);
        return new SqlUnit(sql,sqlParameterCollector.getParameters(),entities,fillAutoIncrement);
    }
}
