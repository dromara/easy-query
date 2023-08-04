package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnInsertSegmentImpl implements ColumnInsertSegment {


    protected final TableAvailable table;
    protected final String propertyName;
    protected final QueryRuntimeContext runtimeContext;
    protected final ColumnMetadata columnMetadata;

    public ColumnInsertSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext,new PropertySQLParameter(table,propertyName));
        return "?";
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
    public SQLEntitySegment cloneSQLColumnSegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public String toInsertColumn(ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,columnMetadata.getName(),toSQLContext);
    }
}
