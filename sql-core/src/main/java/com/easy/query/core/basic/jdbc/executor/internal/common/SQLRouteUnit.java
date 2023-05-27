package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;

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
    private final SQLRewriteUnit sqlRewriteUnit;
    private final EntitySQLExpression easyEntitySQLExpression;
    private final SQLUnit sqlUnit;

    public SQLRouteUnit(String sql, List<SQLParameter> parameters){
        this(sql,parameters,null,false);
    }
    public SQLRouteUnit(String sql, List<SQLParameter> parameters, List<Object> entities, boolean fillAutoIncrement) {
        this.sql = sql;
        this.parameters = parameters;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
        this.sqlRewriteUnit = null;
        this.easyEntitySQLExpression = null;
        this.sqlUnit=new SQLUnit(sql,parameters,entities,fillAutoIncrement);
    }

    public SQLRouteUnit(EntitySQLExpression easyEntitySQLExpression,List<Object> entities, boolean fillAutoIncrement,SQLRewriteUnit sqlRewriteUnit){
        this.easyEntitySQLExpression = easyEntitySQLExpression;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
        this.sqlRewriteUnit = sqlRewriteUnit;
        this.sql = null;
        this.parameters = null;
        this.sqlUnit = null;
    }

    public EntitySQLExpression getEasyEntitySQLExpression() {
        return easyEntitySQLExpression;
    }
    public SQLUnit getSQLUnit(){
        if(sqlUnit!=null){
            return sqlUnit;
        }
        assert easyEntitySQLExpression != null;
        TableContext tableContext = easyEntitySQLExpression.getExpressionMetadata().getTableContext();
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(tableContext,sqlRewriteUnit);
        String sql = easyEntitySQLExpression.toSQL(toSQLContext);
        return new SQLUnit(sql,toSQLContext.getParameters(),entities,fillAutoIncrement);
    }
}
