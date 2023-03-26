package com.easy.query.core.expression.sql.internal;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.sql.SqlEntityExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.expression.sql.SqlExpressionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractSqlEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:58
 * @author xuejiaming
 */
public abstract class AbstractSqlEntityExpression implements SqlEntityExpression {
    protected final SqlExpressionContext sqlExpressionContext;
    protected final List<SqlEntityTableExpression> tables;
    protected boolean logicDelete;

    public AbstractSqlEntityExpression(SqlExpressionContext sqlExpressionContext){
        this.sqlExpressionContext = sqlExpressionContext;
        this.tables = new ArrayList<>();
        logicDelete=true;
    }

    @Override
    public SqlExpressionContext getSqlExpressionContext() {
        return sqlExpressionContext;
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return sqlExpressionContext.getRuntimeContext();
    }

    @Override
    public void addSqlEntityTableExpression(SqlEntityTableExpression tableExpression) {
        tables.add(tableExpression);
    }

    @Override
    public List<SqlEntityTableExpression> getTables() {
        return tables;
    }

    @Override
    public SqlEntityTableExpression getTable(int index) {
        return tables.get(index);
    }

    public String getQuoteName(String value) {
        return sqlExpressionContext.getQuoteName(value);
    }

    @Override
    public String getSqlOwnerColumn(SqlEntityTableExpression table, String propertyName) {
        String alias = table.getAlias();
        String columnName = table.getColumnName(propertyName);
        String quoteName = getQuoteName(columnName);
        if (alias == null) {
            return quoteName;
        } else {
            return alias + "." + quoteName;
        }
    }
    @Override
    public List<SQLParameter> getParameters() {
        return sqlExpressionContext.getParameters();
    }

    @Override
    public void addParameter(SQLParameter parameter) {
        sqlExpressionContext.addParameter(parameter);
    }
    @Override
    public void setLogicDelete(boolean logicDelete) {
        this.logicDelete = logicDelete;
    }
}
