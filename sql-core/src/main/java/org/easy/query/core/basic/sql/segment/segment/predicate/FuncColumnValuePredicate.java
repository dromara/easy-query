package org.easy.query.core.basic.sql.segment.segment.predicate;

import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.abstraction.sql.enums.IEasyPredicate;
import org.easy.query.core.impl.SqlPredicateContext;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class FuncColumnValuePredicate implements Predicate {
    private final int index;
    private final IEasyFunc func;
    private final String column;
    private final Object val;
    private final IEasyPredicate compare;
    private final SqlPredicateContext sqlPredicateContext;

    public FuncColumnValuePredicate(int index, IEasyFunc func, String column, Object val, IEasyPredicate compare, SqlPredicateContext sqlPredicateContext) {

        this.index = index;
        this.func = func;
        this.column = column;
        this.val = val;
        this.compare = compare;
        this.sqlPredicateContext = sqlPredicateContext;
    }

    @Override
    public String getSql() {
        sqlPredicateContext.addParameter(val);
        String sqlColumnSegment = sqlPredicateContext.getSqlColumnSegment(index,column);
        return func.getFuncColumn(sqlColumnSegment) +" "+ compare.getPredicate() + " ?";
    }
}
