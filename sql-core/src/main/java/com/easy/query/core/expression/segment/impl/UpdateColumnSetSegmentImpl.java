package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/7 10:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateColumnSetSegmentImpl implements InsertUpdateSetColumnSQLSegment {
    protected final TableAvailable table;
    protected final String propertyName;
    protected final Object val;
    protected final QueryRuntimeContext runtimeContext;

    public UpdateColumnSetSegmentImpl(TableAvailable table, String propertyName, Object val, QueryRuntimeContext runtimeContext){

        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateColumnSetSegmentImpl(table,propertyName,val,runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
        toSQLContext.addParameter(constSQLParameter);
        return "?";
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
