package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.config.JDQCConfiguration;
import org.jdqc.sql.core.exception.JDQCException;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @FileName: SelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:39
 * @Created by xuejiaming
 */
public class SelectContext extends BaseSelectContext {
    private final JDQCConfiguration jdqcConfiguration;
    private final String alias;
    private int skip;
    private int take;

    private final List<SelectTableInfo> tables;
    private final Class<?> resultClass;

    private final StringBuilder where;

    public SelectContext(Class<?> resultClass, JDQCConfiguration jdqcConfiguration){
        this(resultClass,jdqcConfiguration,"t");
    }
    public SelectContext(Class<?> resultClass, JDQCConfiguration jdqcConfiguration,String alias){
        super(jdqcConfiguration);
        this.jdqcConfiguration = jdqcConfiguration;
        this.alias = alias;
        this.tables =new ArrayList<>();
        this.resultClass=resultClass;
        this.where= new StringBuilder();
    }

    public List<SelectTableInfo> getTables() {
        return tables;
    }
    public SelectTableInfo getTable(int index) {
        return tables.get(index);
    }
    public Class<?> getResultClass() {
        return resultClass;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public StringBuilder getWhere() {
        return where;
    }

    public Class<?> getMainClass() {
        return tables.get(0).getTable().getTableType();
    }
    public void addSelectTable(SelectTableInfo selectTableInfo){
        this.tables.add(selectTableInfo);
    }

    /**
     * 数据库别名 默认t
     * @return
     */

    public String getAlias() {
        return alias;
    }

    /**
     * 获取下次表索引
     * @return
     */
    public int getNextTableIndex(){
        return this.tables.size();
    }
    public SelectTableInfo getCurrentPredicateTable(){
        return this.getPredicateTableByOffset(0);
    }
    public SelectTableInfo getPreviewPredicateTable(){
        return this.getPredicateTableByOffset(1);
    }
    public SelectTableInfo getPredicateTableByOffset(int offsetForward){
        if(this.tables.isEmpty()){
            throw new JDQCException("cant get current join table");
        }
        int i = getNextTableIndex() -1 - offsetForward;
        return this.tables.get(i);
    }
}
