package com.easy.query.core.expression.segment;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;
import com.easy.query.core.util.SqlUtil;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithSelfSegment implements SqlEntitySegment {
    private static final String INCREMENT="+?";
    private static final String DECREMENT="-?";
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final EasyQueryRuntimeContext runtimeContext;
    private final String selfLink;
    private final EntityTableAvailable entityTable;

    public ColumnWithSelfSegment(boolean increment, EntityTableAvailable entityTable, String propertyName, Object val, SqlPredicateCompare compare, EasyQueryRuntimeContext runtimeContext) {
        this.selfLink=increment?INCREMENT:DECREMENT;
        this.entityTable = entityTable;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SqlUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(entityTable,propertyName,val));
        String sqlColumnSegment1 = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,entityTable,propertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment1+selfLink;
    }

    @Override
    public EntityTableAvailable getTable() {
        return entityTable;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {
        throw new UnsupportedOperationException();
    }
}
