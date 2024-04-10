package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
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

    public SQLRouteUnit(String sql, List<SQLParameter> parameters){
        this(sql,parameters,null,false);
    }
    public SQLRouteUnit(String sql, List<SQLParameter> parameters, List<Object> entities, boolean fillAutoIncrement) {
        this.sql = sql;
        this.parameters = parameters;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
    }

    public SQLRouteUnit(EntitySQLExpression easyEntitySQLExpression,List<Object> entities, boolean fillAutoIncrement,SQLRewriteUnit sqlRewriteUnit){
        TableContext tableContext = easyEntitySQLExpression.getExpressionMetadata().getTableContext();
        boolean query=easyEntitySQLExpression instanceof EntityQuerySQLExpression;
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(tableContext,sqlRewriteUnit,query);
        String sql = easyEntitySQLExpression.toSQL(toSQLContext);
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
        this.sql = sql;
        this.parameters = toSQLContext.getParameters();
    }

    public String getSQL() {
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
