package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithColumnPredicate implements Predicate {
    private final TableAvailable leftTable;
    private final String leftPropertyName;
    private final TableAvailable rightTable;
    private final String rightPropertyName;
    private final SQLPredicateCompare compare;
    private final QueryRuntimeContext runtimeContext;

    public ColumnWithColumnPredicate(TableAvailable leftTable, String leftPropertyName, TableAvailable rightTable, String rightPropertyName, SQLPredicateCompare compare, QueryRuntimeContext runtimeContext) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        String sqlColumnSegment1 =  EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,leftTable,leftPropertyName,toSQLContext);
        String sqlColumnSegment2 =  EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,rightTable,rightPropertyName,toSQLContext);
        return sqlColumnSegment1 +" "+ compare.getSQL() + " "+sqlColumnSegment2;
    }

    @Override
    public TableAvailable getTable() {
        return leftTable;
    }

    @Override
    public String getPropertyName() {
        return leftPropertyName;
    }

    @Override
    public SQLEntitySegment cloneSQLEntitySegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }
}
