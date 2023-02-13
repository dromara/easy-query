package org.easy.query.core.segments;

import org.easy.query.core.abstraction.SqlSegment0;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;

/**
 * @FileName: PredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:21
 * @Created by xuejiaming
 */
public class PredicateSegment implements SqlSegment0 {
    private final int index;
    private final String columnName;
    private final SelectContext selectContext;
    private final SqlKeywordEnum sqlKeyword;

    private int compareIndex;
    private Object compareValue;

    public PredicateSegment(int index, String columnName, SelectContext selectContext, SqlKeywordEnum sqlKeyword){
        this.index = index;

        this.columnName = columnName;
        this.selectContext = selectContext;
        this.sqlKeyword = sqlKeyword;
    }

    @Override
    public String toSql() {
        SelectTableInfo table = selectContext.getTable(index);
        String quoteName = selectContext.getQuoteName(columnName);
        StringBuilder sb = new StringBuilder().append(table.getAlias()).append(".").append(quoteName).append(" ").append(sqlKeyword.getKeyword());
       if(compareIndex>=0){

           SelectTableInfo compareTable = selectContext.getTable(compareIndex);
           String compareQuoteName = selectContext.getQuoteName(compareValue.toString());
           sb.append(" ").append(compareTable.getAlias()).append(".").append(compareQuoteName);
       }else if(compareValue!=null){
           sb.append(" ?");
           selectContext.addParams(compareValue);
       }
        return sb.toString();
    }

    @Override
    public int getIndex() {
        return index;
    }


    public int getCompareIndex() {
        return compareIndex;
    }

    public void setCompareIndex(int compareIndex) {
        this.compareIndex = compareIndex;
    }

    public Object getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(Object compareValue) {
        this.compareValue = compareValue;
    }
}
