package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.MapSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/8/7 11:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertMapColumnSegmentImpl implements InsertUpdateSetColumnSQLSegment {

    private final String columnName;
    private final String mapKey;
    private final QueryRuntimeContext runtimeContext;

    public InsertMapColumnSegmentImpl(String columnName,String mapKey, QueryRuntimeContext runtimeContext){
        this.columnName = columnName;
        this.mapKey = mapKey;
        this.runtimeContext = runtimeContext;
    }
    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
       return EasySQLExpressionUtil.getQuoteName(runtimeContext,columnName);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new InsertMapColumnSegmentImpl(columnName,mapKey,runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        MapSQLParameter mapSQLParameter = new MapSQLParameter(mapKey,false);
        EasySQLUtil.addParameter(toSQLContext, mapSQLParameter);
        return "?";
    }

    @Override
    public String getPropertyName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getTable() {
        throw new UnsupportedOperationException();
    }
}
