package org.jdqc.core.impl.lambda;


/**
 * @FileName: PredicateBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/7 14:14
 * @Created by xuejiaming
 */
public abstract class SelectorBuilder {
    public static final String LINK = ", ";


    public abstract StringBuilder getSql() ;
    /**
     * 拼接SQL
     *
     * @param sqlPart
     */
    public SelectorBuilder appendSql(String sqlPart) {
        getSql().append(sqlPart);
        return this;
    }

    /**
     *
     * @param columnOwner
     * @param column
     */
    protected void appendSelectSql(String columnOwner,String column) {
        //判断是否有效的变量
        String link=getSql().length()==0?"":LINK;
        this.appendSql(link).appendSql(getCol(columnOwner,column));
    }
    /**
     * 获取字段 加上前后空格
     *
     * @param columnOwner
     * @param colName
     * @return
     */
    protected String getCol(String columnOwner,String colName) {
        return columnOwner+"."+colName;
    }

}
