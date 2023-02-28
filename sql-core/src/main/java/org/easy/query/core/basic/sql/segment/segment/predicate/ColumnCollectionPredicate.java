package org.easy.query.core.basic.sql.segment.segment.predicate;

import org.easy.query.core.basic.sql.segment.segment.SqlSegment;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.basic.api.context.SqlPredicateContext;

import java.util.Collection;
import java.util.Iterator;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnCollectionPredicate implements Predicate {
    private final int index;
    private final String column;
    private final Collection<?> collection;
    private final SqlSegment compare;
    private final SqlPredicateContext sqlPredicateContext;

    public ColumnCollectionPredicate(int index, String column, Collection<?> collection, SqlSegment compare, SqlPredicateContext sqlPredicateContext) {
        this.index = index;
        this.column = column;
        this.collection = collection;
        this.compare = compare;
        this.sqlPredicateContext = sqlPredicateContext;
    }

    @Override
    public String getSql() {
        if (collection.isEmpty()) {
            if (SqlKeywordEnum.IN.equals(compare)) {
                return "FALSE";
            } else if (SqlKeywordEnum.NOT_IN.equals(compare)) {
                return "TRUE";
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            String sqlColumnSegment = sqlPredicateContext.getSqlColumnSegment(index,column);
            StringBuilder sql = new StringBuilder();
            sql.append(sqlColumnSegment).append(" ").append(compare.getSql()).append(" (");
            Iterator<?> iterator = collection.iterator();
            Object firstVal = iterator.next();
            sqlPredicateContext.addParameter(firstVal);
            sql.append("?");
            while (iterator.hasNext()){
                Object val = iterator.next();
                sqlPredicateContext.addParameter(val);
                sql.append(",?");
            }
            sql.append(")");
            return sql.toString();
        }
    }
}
