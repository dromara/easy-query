package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @author xuejiaming
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 */
public class ColumnSegmentImpl implements ColumnSegment {


    protected final TableAvailable table;


    protected final ColumnMetadata columnMetadata;
    protected final QueryRuntimeContext runtimeContext;
    protected String alias;

    public ColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext) {
        this(table, propertyName, runtimeContext, null);
    }

    public ColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String alias) {
        this(table, propertyName == null ? null : table.getEntityMetadata().getColumnNotNull(propertyName), runtimeContext, alias);
    }

    public ColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, String alias) {
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.runtimeContext = runtimeContext;
        this.alias = alias;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        if (this.columnMetadata == null) {
            return null;
        }
        return this.columnMetadata.getPropertyName();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext, table, getColumnNameOrNull(), toSQLContext);
        if (alias == null) {
            return sqlColumnSegment;
        }
        return sqlColumnSegment + " AS " + EasySQLExpressionUtil.getQuoteName(runtimeContext, alias);
    }
    protected String getColumnNameOrNull(){
        if(columnMetadata==null){
            return null;
        }
        return columnMetadata.getName();
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new ColumnSegmentImpl(table, columnMetadata, runtimeContext, alias);
    }

}
