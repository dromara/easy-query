package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
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
    protected final String alias;

    public ColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext) {
        this(table, columnMetadata, runtimeContext, null);
    }

    public ColumnSegmentImpl(TableAvailable table,ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, String alias) {
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
        return columnMetadata.getPropertyName();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlOwnerColumn = getSQLOwnerColumn(toSQLContext);
        if (alias == null) {
            return sqlOwnerColumn;
        }
        return sqlOwnerColumn + " AS " + EasySQLExpressionUtil.getQuoteName(runtimeContext, alias);
    }

    private String getSQLOwnerColumn(ToSQLContext toSQLContext){

        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if(columnValueSQLConverter==null){
            return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, table, columnMetadata, toSQLContext);
        }else{
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, runtimeContext);
            columnValueSQLConverter.columnConvert(table,columnMetadata,sqlPropertyConverter,runtimeContext);
            return sqlPropertyConverter.toSQL(toSQLContext);
        }
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new ColumnSegmentImpl(table, columnMetadata, runtimeContext, alias);
    }

}
