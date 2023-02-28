package org.easy.query.core.basic.sql.segment.segment;

import org.easy.query.core.basic.api.context.SqlContext;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class ColumnSegment implements SqlSegment {
    private final int index;

    public String getColumnName() {
        return columnName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public SqlContext getSqlContext() {
        return sqlContext;
    }

    private final String columnName;

    private final String propertyName;
    private final SqlContext sqlContext;
    private String alias;

    public ColumnSegment(int index, String columnName,String propertyName, SqlContext sqlContext){
        this(index,columnName,propertyName,sqlContext,null);
    }
    public ColumnSegment(int index, String columnName,String propertyName, SqlContext sqlContext,String alias){
        this.index = index;

        this.columnName = columnName;
        this.propertyName = propertyName;
        this.sqlContext = sqlContext;
        this.alias = alias;
    }

    @Override
    public String getSql() {
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(index,columnName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(sqlContext.getQuoteName(alias));
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
