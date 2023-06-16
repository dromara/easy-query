package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
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

    public ColumnInsertSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){
        this.table = table;

        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext,new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        return sql.toString();
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
    public SQLEntitySegment cloneSQLEntitySegment() {

        throw new UnsupportedOperationException();
    }

}
