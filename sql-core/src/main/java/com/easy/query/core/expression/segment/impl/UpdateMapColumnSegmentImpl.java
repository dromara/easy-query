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
 * @author xuejiaming
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * create time 2023/2/13 15:18
 */
public class UpdateMapColumnSegmentImpl implements InsertUpdateSetColumnSQLSegment {

    private final TableAvailable table;
    private final String columnName;
    private final String mapKey;
    private final QueryRuntimeContext runtimeContext;

    public UpdateMapColumnSegmentImpl(TableAvailable table, String columnName, String mapKey, QueryRuntimeContext runtimeContext) {
        this.table = table;
        this.columnName = columnName;
        this.mapKey = mapKey;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        MapSQLParameter mapSQLParameter = new MapSQLParameter(mapKey, false);
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
        return EasySQLExpressionUtil.getQuoteName(runtimeContext, columnName);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateMapColumnSegmentImpl(table, columnName, mapKey, runtimeContext);
    }

    public String getColumnName() {
        return columnName;
    }

    public String getMapKey() {
        return mapKey;
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
