package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
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
public class InsertColumnSegmentImpl implements InsertUpdateSetColumnSQLSegment {
    protected final TableAvailable table;
    protected final String propertyName;
    protected final QueryRuntimeContext runtimeContext;

    public InsertColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){

        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
    }
    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
       return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new InsertColumnSegmentImpl(table,propertyName,runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext,new PropertySQLParameter(table,propertyName));
        return "?";
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
