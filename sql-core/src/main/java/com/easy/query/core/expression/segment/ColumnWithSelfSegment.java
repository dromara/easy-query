package com.easy.query.core.expression.segment;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.SQLUtil;
import com.easy.query.core.util.SQLExpressionUtil;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithSelfSegment implements SQLEntitySegment {
    private static final String INCREMENT="+?";
    private static final String DECREMENT="-?";
    private final String propertyName;
    private final Object val;
    private final SQLPredicateCompare compare;
    private final QueryRuntimeContext runtimeContext;
    private final String selfLink;
    private final TableAvailable entityTable;

    public ColumnWithSelfSegment(boolean increment, TableAvailable entityTable, String propertyName, Object val, SQLPredicateCompare compare, QueryRuntimeContext runtimeContext) {
        this.selfLink=increment?INCREMENT:DECREMENT;
        this.entityTable = entityTable;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(entityTable,propertyName,val));
        String sqlColumnSegment1 = SQLExpressionUtil.getSQLOwnerColumn(runtimeContext,entityTable,propertyName);
        return sqlColumnSegment1 +" "+ compare.getSQL() + " "+sqlColumnSegment1+selfLink;
    }

    @Override
    public TableAvailable getTable() {
        return entityTable;
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
