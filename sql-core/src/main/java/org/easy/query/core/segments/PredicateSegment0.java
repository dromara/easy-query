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
public class PredicateSegment0 implements SqlSegment0 {
    private final int index;
    private final String columnName;
    private final SelectContext selectContext;
    private final SqlKeywordEnum sqlKeyword;

    private int compareIndex=-1;
    private Object compareValue;

    private PredicateSegment0(int index, String columnName, SelectContext selectContext, SqlKeywordEnum sqlKeyword){
        this.index = index;

        this.columnName = columnName;
        this.selectContext = selectContext;
        this.sqlKeyword = sqlKeyword;
    }
    public static PredicateSegment0 createColumn2Value(int index, String columnName, SelectContext selectContext, SqlKeywordEnum sqlKeyword, Object val){
        PredicateSegment0 predicateSegment = new PredicateSegment0(index, columnName, selectContext, sqlKeyword);
        predicateSegment.setCompareValue(val);
        return predicateSegment;
    }
    public static PredicateSegment0 createColumn2Column(int index, String columnName, SelectContext selectContext, SqlKeywordEnum sqlKeyword, int targetIndex, String targetColumn){
        PredicateSegment0 predicateSegment = new PredicateSegment0(index, columnName, selectContext, sqlKeyword);
        predicateSegment.setCompareIndex(targetIndex);
        predicateSegment.setCompareValue(targetColumn);
        return predicateSegment;
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

    public SqlKeywordEnum getSqlKeyword() {
        return sqlKeyword;
    }

}
