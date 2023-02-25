package org.easy.query.core.basic.sql.segment.segment.predicate;

import org.easy.query.core.basic.sql.segment.segment.SqlSegment;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.impl.SqlPredicateContext;

import java.util.Collection;

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
            int i = 0;
            for (Object val : collection) {
                sqlPredicateContext.addParameter(val);
                sql.append(i == 0 ? "?" : ",?");
                i++;
            }
            sql.append(")");
            return sql.toString();
        }
    }
}
