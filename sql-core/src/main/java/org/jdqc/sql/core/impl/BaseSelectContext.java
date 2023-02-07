package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.config.JDQCConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @FileName: BaseSelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:23
 * @Created by xuejiaming
 */
public class BaseSelectContext {
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String WHERE = "WHERE";
    public static final String IN = "IN";
    public static final String NOT_IN = "NOT IN";
    public static final String BETWEEN = "BETWEEN";
    public static final String NOT_BETWEEN = "NOT BETWEEN";
    private final JDQCConfiguration jdqcConfiguration;
    protected StringBuilder sql = null;
    protected List<Object> params = new ArrayList<>();

    public BaseSelectContext(JDQCConfiguration jdqcConfiguration){

        this.jdqcConfiguration = jdqcConfiguration;
    }

    public StringBuilder getSql() {
        if (this.sql == null) {
            return new StringBuilder();
        }
        return this.sql;
    }
    /**
     * 拼接SQL
     *
     * @param sqlPart
     */
    public BaseSelectContext appendSql(String sqlPart) {
        if (this.sql == null) {
            this.sql = new StringBuilder();
        }
        sql.append(sqlPart);
        return this;
    }


    /**
     * 增加参数
     *
     * @param object
     * @return
     */
    public BaseSelectContext addParam(Object object) {
        params.add(object);
        return this;
    }
    public void appendAndSql(String columnOwner,String column, Object value, String compare){
        appendSqlBase(columnOwner,column,value,compare,AND);
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

        if (getSql().indexOf(WHERE) < 0) {
            link = WHERE;
        }
        this.appendSql(link).appendSql(getCol(columnOwner,column)).appendSql(compare);
        if (value != null) {
            this.appendSql(" ? ");
            this.addParam(value);
        }
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

    public JDQCConfiguration getJdqcConfiguration() {
        return jdqcConfiguration;
    }
}
