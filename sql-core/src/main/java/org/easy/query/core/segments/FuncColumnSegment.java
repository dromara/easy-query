package org.easy.query.core.segments;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.impl.SqlContext;
import org.easy.query.core.query.builder.SqlTableInfo;

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

    public SqlContext getSqlContext() {
        return sqlContext;
    }

    private final String columnName;
    private final SqlContext sqlContext;
    private final IEasyFunc easyFunc;
    private String alias;

    public FuncColumnSegment(int index, String columnName, SqlContext sqlContext, IEasyFunc easyFunc){
        this(index,columnName,sqlContext,easyFunc,null);
    }
    public FuncColumnSegment(int index, String columnName, SqlContext sqlContext, IEasyFunc easyFunc,String alias){
        this.index = index;

        this.columnName = columnName;
        this.sqlContext = sqlContext;
        this.easyFunc = easyFunc;
        this.alias = alias;
    }

    @Override
    public String getSql() {
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(index,columnName);
        String funcColumn = easyFunc.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(getAlias()!=null){
            sql.append(" AS ").append(sqlContext.getQuoteName(getAlias()));
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
