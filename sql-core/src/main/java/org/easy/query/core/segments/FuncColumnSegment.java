package org.easy.query.core.segments;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @Created by xuejiaming
 */
public class FuncColumnSegment  implements SqlSegment {
    private final int index;

    public String getColumnName() {
        return columnName;
    }

    public SelectContext getSelectContext() {
        return selectContext;
    }

    private final String columnName;
    private final SelectContext selectContext;
    private final IEasyFunc easyFunc;
    private String alias;

    public FuncColumnSegment(int index, String columnName, SelectContext selectContext, IEasyFunc easyFunc){
        this(index,columnName,selectContext,easyFunc,null);
    }
    public FuncColumnSegment(int index, String columnName, SelectContext selectContext, IEasyFunc easyFunc,String alias){
        this.index = index;

        this.columnName = columnName;
        this.selectContext = selectContext;
        this.easyFunc = easyFunc;
        this.alias = alias;
    }

    @Override
    public String getSql() {
        SelectTableInfo table = selectContext.getTable(index);
        String quoteName = selectContext.getQuoteName(columnName);
        String funcColumn = easyFunc.getFuncColumn(table.getAlias() + "." + quoteName);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(getAlias()!=null){
            sql.append(" AS ").append(selectContext.getQuoteName(getAlias()));
        }
        return sql.toString();
    }

    public int getIndex() {
        return index;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
