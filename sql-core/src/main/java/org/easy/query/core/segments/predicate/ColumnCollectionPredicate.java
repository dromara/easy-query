package org.easy.query.core.segments.predicate;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SqlTableInfo;

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
    private final SelectContext selectContext;

    public ColumnCollectionPredicate(int index, String column, Collection<?> collection, SqlSegment compare, SelectContext selectContext) {
        this.index = index;
        this.column = column;
        this.collection = collection;
        this.compare = compare;
        this.selectContext = selectContext;
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
            SqlTableInfo table = selectContext.getTable(index);
            String quoteName = selectContext.getQuoteName(column);
            StringBuilder sql = new StringBuilder();
            sql.append(table.getAlias()).append(".").append(quoteName).append(" ").append(compare.getSql()).append(" (");
            int i = 0;
            for (Object val : collection) {
                selectContext.addParams(val);
                sql.append(i == 0 ? "?" : ",?");
                i++;
            }
            sql.append(")");
            return sql.toString();
        }
    }
}
