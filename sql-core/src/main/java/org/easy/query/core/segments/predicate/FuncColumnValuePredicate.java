package org.easy.query.core.segments.predicate;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.abstraction.sql.enums.IEasyPredicate;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;

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
    private final SelectContext selectContext;

    public FuncColumnValuePredicate(int index, IEasyFunc func, String column, Object val, IEasyPredicate compare, SelectContext selectContext) {

        this.index = index;
        this.func = func;
        this.column = column;
        this.val = val;
        this.compare = compare;
        this.selectContext = selectContext;
        selectContext.addParams(val);
    }

    @Override
    public String getSql() {
        SelectTableInfo table = selectContext.getTable(index);
        String quoteName = selectContext.getQuoteName(column);
        return func.getFuncColumn(table.getAlias() + "." + quoteName) +" "+ compare.getPredicate() + " ?";
    }
}
