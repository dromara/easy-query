package com.easy.query.core.expression.segment;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.SQLExpressionUtil;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnSegmentImpl implements ColumnSegment {


    protected final TableAvailable table;


    protected final String propertyName;
    protected final EasyQueryRuntimeContext runtimeContext;
    protected String alias;

    public ColumnSegmentImpl(TableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext){
        this(table,propertyName,runtimeContext,null);
    }
    public ColumnSegmentImpl(TableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext, String alias){
        this.table = table;

        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.alias = alias;
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
    public String getAlias() {
        return alias;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        String sqlColumnSegment = SQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(SQLExpressionUtil.getQuoteName(runtimeContext,alias));
        }
        return sql.toString();
    }

    @Override
    public ColumnSegment cloneSQLEntitySegment() {
        return new ColumnSegmentImpl(table,propertyName, runtimeContext,alias);
    }

}
