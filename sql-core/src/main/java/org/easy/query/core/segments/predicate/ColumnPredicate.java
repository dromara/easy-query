package org.easy.query.core.segments.predicate;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.SqlContext;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnPredicate implements Predicate {
    private final int index;
    private final String column;
    private final SqlSegment compare;
    private final SqlContext sqlContext;

    public ColumnPredicate(int index, String column, SqlSegment compare, SqlContext sqlContext) {

        this.index = index;
        this.column = column;
        this.compare = compare;
        this.sqlContext = sqlContext;
    }

    @Override
    public String getSql() {
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(index,column);
        return sqlColumnSegment +" "+ compare.getSql();
    }
}
