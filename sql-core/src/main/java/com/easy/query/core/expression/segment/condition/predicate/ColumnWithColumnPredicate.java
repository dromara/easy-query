package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.parser.abstraction.internal.EntityTableAvailable;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithColumnPredicate implements Predicate {
    private final EntityTableAvailable leftTable;
    private final String leftPropertyName;
    private final EntityTableAvailable rightTable;
    private final String rightPropertyName;
    private final SqlPredicateCompare compare;
    private final EasyQueryRuntimeContext runtimeContext;

    public ColumnWithColumnPredicate(EntityTableAvailable leftTable, String leftPropertyName, EntityTableAvailable rightTable, String rightPropertyName, SqlPredicateCompare compare, EasyQueryRuntimeContext runtimeContext) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        String sqlColumnSegment1 =  SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,leftTable,leftPropertyName);
        String sqlColumnSegment2 =  SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,rightTable,rightPropertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment2;
    }

    @Override
    public EntityTableAvailable getTable() {
        return leftTable;
    }

    @Override
    public String getPropertyName() {
        return leftPropertyName;
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlPredicateCompare getOperator() {
        return compare;
    }
}
