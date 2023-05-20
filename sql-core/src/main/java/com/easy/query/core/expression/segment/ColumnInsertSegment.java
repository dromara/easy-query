package com.easy.query.core.expression.segment;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnInsertSegment implements SQLEntityAliasSegment {


    protected final TableAvailable table;
    protected final String propertyName;
    protected final QueryRuntimeContext runtimeContext;
    protected String alias;

    public ColumnInsertSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){
        this(table,propertyName,runtimeContext,null);
    }
    public ColumnInsertSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String alias){
        this.table = table;

        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.alias = alias;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext,new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(EasySQLExpressionUtil.getQuoteName(runtimeContext,alias));
        }
        return sql.toString();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SQLEntitySegment cloneSQLEntitySegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getAlias() {
        return alias;
    }
}
