package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.basic.jdbc.parameter.DefaultSQLParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.expression.sql.expression.EasyEntitySQLExpression;

import java.util.List;

/**
 * create time 2023/4/20 09:19
 * sql执行单元
 *
 * @author xuejiaming
 */
public class SQLRouteUnit {

    private final String sql;
    private final List<SQLParameter> parameters;
    private final List<Object> entities;
    private final boolean fillAutoIncrement;
    private final EasyEntitySQLExpression easyEntitySQLExpression;
    private final SQLUnit sqlUnit;

    public SQLRouteUnit(String sql, List<SQLParameter> parameters){
        this(sql,parameters,null,false);
    }
    public SQLRouteUnit(String sql, List<SQLParameter> parameters, List<Object> entities, boolean fillAutoIncrement) {
        this.sql = sql;
        this.parameters = parameters;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
        this.easyEntitySQLExpression = null;
        this.sqlUnit=new SQLUnit(sql,parameters,entities,fillAutoIncrement);
    }

    public SQLRouteUnit(EasyEntitySQLExpression easyEntitySQLExpression, List<Object> entities, boolean fillAutoIncrement){
        this.easyEntitySQLExpression = easyEntitySQLExpression;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
        this.sql = null;
        this.parameters = null;
        this.sqlUnit = null;
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

    public EasyEntitySQLExpression getEasyEntitySQLExpression() {
        return easyEntitySQLExpression;
    }
    public SQLUnit getSQLUnit(){
        if(sqlUnit!=null){
            return sqlUnit;
        }
        SQLParameterCollector sqlParameterCollector = DefaultSQLParameterCollector.defaultCollector();
        assert easyEntitySQLExpression != null;
        String sql = easyEntitySQLExpression.toSQL(sqlParameterCollector);
        return new SQLUnit(sql,sqlParameterCollector.getParameters(),entities,fillAutoIncrement);
    }
}
