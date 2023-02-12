package org.easy.query.core.impl.lambda;


import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.impl.SelectContext;

/**
 * @FileName: PredicateBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/7 14:14
 * @Created by xuejiaming
 */
public abstract class PredicateBuilder extends AbstractSqlSegmentBuilder {
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String WHERE = "WHERE";
    public static final String IN = "IN";
    public static final String NOT_IN = "NOT IN";
    public static final String BETWEEN = "BETWEEN";
    public static final String NOT_BETWEEN = "NOT BETWEEN";
    private final SelectContext selectContext;

    public PredicateBuilder(SelectContext selectContext, SqlSegment sqlSegment){
        super(sqlSegment);

        this.selectContext = selectContext;
    }

    /**
     * 拼接SQL
     *
     * @param sqlPart
     */
    public PredicateBuilder appendSql(String sqlPart) {
        getSqlSegment().append(sqlPart);
        return this;
    }


    /**
     * 增加参数
     *
     * @param object
     * @return
     */
    public PredicateBuilder addParam(Object object) {
        selectContext.getParams().add(object);
        return this;
    }
    public void appendAndSql(String columnOwner,String column, Object value, String compare){
        appendSqlBase(columnOwner,column,value,compare,AND);
    }
    public void appendAndOnSql(String column1Owner,String column1, String column2Owner,String column2, String compare){
        appendOnSqlBase(column1Owner,column1,column2Owner,column2,compare,AND);
    }
    public void appendOrSql(String columnOwner,String column, Object value, String compare){
        appendSqlBase(columnOwner,column,value,compare,OR);
    }

    /**
     *
     * @param columnOwner
     * @param column
     * @param value
     * @param compare
     * @param link 链接 AND OR
     */
    protected void appendSqlBase(String columnOwner,String column, Object value, String compare, String link) {
        //判断是否有效的变量

        if (getSqlSegment().isEmpty()) {
            link = "";
        }
        this.appendSql(link).appendSql(getCol(columnOwner,column)).appendSql(compare);
        if (value != null) {
            this.appendSql(" ? ");
            this.addParam(value);
        }
    }
    protected void appendOnSqlBase(String column1Owner,String column1,String column2Owner,String column2, String compare, String link) {
        //判断是否有效的变量

        if (getSqlSegment().isEmpty()) {
            link = "";
        }
        this.appendSql(link).appendSql(getCol(column1Owner,column1)).appendSql(compare).appendSql(getCol(column2Owner,column2));
    }
    /**
     * 获取字段 加上前后空格
     *
     * @param columnOwner
     * @param colName
     * @return
     */
    protected String getCol(String columnOwner,String colName) {
        return " " + columnOwner+"."+colName + " ";
    }

}
