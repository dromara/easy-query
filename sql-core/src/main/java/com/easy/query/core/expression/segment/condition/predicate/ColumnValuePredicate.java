package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.SQLUtil;

import java.util.Objects;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnValuePredicate implements ValuePredicate, ShardingPredicate {
    private final EntityTableExpressionBuilder table;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final EntityExpressionBuilder sqlEntityExpression;

    public ColumnValuePredicate(EntityTableExpressionBuilder table, String propertyName, Object val, SqlPredicateCompare compare, EntityExpressionBuilder sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
        String compareSql = compare.getSql();
        if(Objects.equals(SqlPredicateCompareEnum.LIKE.getSql(),compareSql)){
            SQLUtil.addParameter(sqlParameterCollector,new ConstLikeSQLParameter(constSQLParameter));
        }else{
            SQLUtil.addParameter(sqlParameterCollector,constSQLParameter);
        }
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment + " " + compareSql + " ?";
    }

    @Override
    public EntityTableExpressionBuilder getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public SQLParameter getParameter() {
        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
        String compareSql = compare.getSql();
        if(Objects.equals(SqlPredicateCompareEnum.LIKE.getSql(),compareSql)) {
             return new ConstLikeSQLParameter(constSQLParameter);
        }
        return constSQLParameter;
    }
}
