package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.enums.SqlPredicateCompareEnum;
import org.easy.query.core.query.SqlEntityExpressionSegment;
import org.easy.query.core.query.SqlEntityTableExpressionSegment;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.Collection;
import java.util.Iterator;

/**
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnCollectionPredicate0 implements Predicate {
    private final Collection<?> collection;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpressionSegment sqlContext;
    private final SqlEntityTableExpressionSegment table;
    private final String propertyName;

    public ColumnCollectionPredicate0(SqlEntityTableExpressionSegment table, String propertyName, Collection<?> collection, SqlPredicateCompare compare, SqlEntityExpressionSegment sqlContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.collection = collection;
        this.compare = compare;
        this.sqlContext = sqlContext;
    }

    @Override
    public String toSql() {
        if (collection.isEmpty()) {
            if (SqlPredicateCompareEnum.IN.equals(compare)) {
                return "FALSE";
            } else if (SqlPredicateCompareEnum.NOT_IN.equals(compare)) {
                return "TRUE";
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            String sqlColumnSegment = sqlContext.getSqlColumnSegment(table,propertyName);
            StringBuilder sql = new StringBuilder();
            sql.append(sqlColumnSegment).append(" ").append(compare.getSql()).append(" (");
            Iterator<?> iterator = collection.iterator();
            Object firstVal = iterator.next();
            sqlContext.addParameter(new ConstSQLParameter(table,propertyName,firstVal));
            sql.append("?");
            while (iterator.hasNext()){
                Object val = iterator.next();
                sqlContext.addParameter(new ConstSQLParameter(table,propertyName,val));
                sql.append(",?");
            }
            sql.append(")");
            return sql.toString();
        }
    }
}
