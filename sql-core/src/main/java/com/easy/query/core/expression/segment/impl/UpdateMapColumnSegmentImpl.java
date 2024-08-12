package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.MapSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class UpdateMapColumnSegmentImpl implements InsertUpdateSetColumnSQLSegment {

    private final TableAvailable table;
    private final String columnName;
    private final QueryRuntimeContext runtimeContext;

    public UpdateMapColumnSegmentImpl(TableAvailable table, String columnName, QueryRuntimeContext runtimeContext){
        this.table = table;
        this.columnName = columnName;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        MapSQLParameter mapSQLParameter = new MapSQLParameter(columnName,false);
        EasySQLUtil.addParameter(toSQLContext, mapSQLParameter);
        return "?";
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    public String getPropertyName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getQuoteName(runtimeContext,columnName);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateMapColumnSegmentImpl(table,columnName,runtimeContext);
    }
}
