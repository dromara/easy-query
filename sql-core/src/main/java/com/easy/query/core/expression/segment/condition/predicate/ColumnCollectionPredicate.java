package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.SQLUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnCollectionPredicate implements ValuesPredicate,ShardingPredicate {
    private final Collection<?> collection;
    private final SqlPredicateCompare compare;
    private final EntityExpressionBuilder sqlEntityExpression;
    private final EntityTableExpressionBuilder table;
    private final String propertyName;

    public ColumnCollectionPredicate(EntityTableExpressionBuilder table, String propertyName, Collection<?> collection, SqlPredicateCompare compare, EntityExpressionBuilder sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;
        this.collection = collection;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        if (ArrayUtil.isEmpty(collection)) {
            if (SqlPredicateCompareEnum.IN.equals(compare)) {
                return "FALSE";
            } else if (SqlPredicateCompareEnum.NOT_IN.equals(compare)) {
                return "TRUE";
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
            StringBuilder sql = new StringBuilder();
            sql.append(sqlColumnSegment).append(" ").append(compare.getSql()).append(" (");
            Iterator<?> iterator = collection.iterator();
            Object firstVal = iterator.next();
            SQLUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,firstVal));
            sql.append("?");
            while (iterator.hasNext()){
                Object val = iterator.next();
                SQLUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,val));
                sql.append(",?");
            }
            sql.append(")");
            return sql.toString();
        }
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
    public Collection<SQLParameter> getParameters() {
        if (ArrayUtil.isEmpty(collection)){
            return Collections.emptyList();
        }
        ArrayList<SQLParameter> sqlParameters = new ArrayList<>(collection.size());
        for (Object o : collection) {
            sqlParameters.add(new EasyConstSQLParameter(table,propertyName,o));
        }
        return sqlParameters;
    }
}
