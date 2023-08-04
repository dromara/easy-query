package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.AbstractSQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/4 22:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeInsertSegmentImpl extends AbstractSQLNativeSegmentImpl implements ColumnInsertSegment {
    protected final TableAvailable table;
    protected final String propertyName;

    public SQLNativeInsertSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpressionContext sqlConstExpressionContext) {
        super(runtimeContext, sqlSegment, sqlConstExpressionContext);
        this.table = table;
        this.propertyName = propertyName;
    }

    @Override
    public TableAvailable getTable() {
        return this.table;
    }

    @Override
    public String getPropertyName() {
        return this.propertyName;
    }

    @Override
    public String toInsertColumn(ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
    }

    @Override
    public SQLColumnSegment cloneSQLColumnSegment() {
        throw new UnsupportedOperationException();
    }
}
