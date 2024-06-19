package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
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
    protected final ExpressionContext expressionContext;
    protected String alias;

    public ColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext) {
        this(table, columnMetadata, expressionContext, null);
    }

    public ColumnSegmentImpl(TableAvailable table,ColumnMetadata columnMetadata, ExpressionContext expressionContext, String alias) {
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.expressionContext = expressionContext;
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
    public void setAlias(String alias) {
        this.alias=alias;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlOwnerColumn = getSQLOwnerColumn(toSQLContext);
        if (getAlias() == null) {
            return sqlOwnerColumn;
        }
        return sqlOwnerColumn + " AS " + EasySQLExpressionUtil.getQuoteName(expressionContext.getRuntimeContext(), getAlias());
    }

    private String getSQLOwnerColumn(ToSQLContext toSQLContext){
        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext,ignoreAlias(),true);
    }

    protected boolean ignoreAlias(){
        if(getAlias() != null){
            return true;
        }
        return false;
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new ColumnSegmentImpl(table, columnMetadata, expressionContext, alias);
    }

}
