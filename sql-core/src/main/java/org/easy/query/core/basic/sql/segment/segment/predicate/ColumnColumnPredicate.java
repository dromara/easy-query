package org.easy.query.core.basic.sql.segment.segment.predicate;

import org.easy.query.core.basic.sql.segment.segment.SqlSegment;
import org.easy.query.core.impl.SqlContext;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnColumnPredicate implements Predicate {
    private final int index;
    private final String column;
    private final int compareIndex;
    private final String compareColumn;
    private final SqlSegment compare;
    private final SqlContext sqlContext;

    public ColumnColumnPredicate(int index, String column,int compareIndex, String compareColumn, SqlSegment compare, SqlContext sqlContext) {

        this.index = index;
        this.column = column;
        this.compareIndex = compareIndex;
        this.compareColumn = compareColumn;
        this.compare = compare;
        this.sqlContext = sqlContext;
    }

    @Override
    public String getSql() {
        String sqlColumnSegment1 = sqlContext.getSqlColumnSegment(index,column);
        String sqlColumnSegment2 = sqlContext.getSqlColumnSegment(compareIndex,compareColumn);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment2;
    }
}
