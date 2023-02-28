package org.easy.query.core.expression.segment.predicate.node;

import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.enums.SqlPredicateCompareEnum;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.Collection;
import java.util.Iterator;

/**
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnCollectionPredicate implements Predicate {
    private final Collection<?> collection;
    private final SqlPredicateCompare compare;
    private final SqlContext sqlContext;
    private final SqlTableInfo table;
    private final String propertyName;

    public ColumnCollectionPredicate(SqlTableInfo table, String propertyName, Collection<?> collection, SqlPredicateCompare compare, SqlContext sqlContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.collection = collection;
        this.compare = compare;
        this.sqlContext = sqlContext;
    }

    @Override
    public String getSql() {
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
