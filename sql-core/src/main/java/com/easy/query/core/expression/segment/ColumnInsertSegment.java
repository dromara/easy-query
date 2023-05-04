package com.easy.query.core.expression.segment;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;
import com.easy.query.core.util.SqlUtil;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnInsertSegment implements SqlEntityAliasSegment {


    protected final EntityTableAvailable table;
    protected final String propertyName;
    protected final EasyQueryRuntimeContext runtimeContext;
    protected String alias;

    public ColumnInsertSegment(EntityTableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext){
        this(table,propertyName,runtimeContext,null);
    }
    public ColumnInsertSegment(EntityTableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext, String alias){
        this.table = table;

        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.alias = alias;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SqlUtil.addParameter(sqlParameterCollector,new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(SqlExpressionUtil.getQuoteName(runtimeContext,alias));
        }
        return sql.toString();
    }

    @Override
    public EntityTableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getAlias() {
        return alias;
    }
}
