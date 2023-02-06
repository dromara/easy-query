package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.common.TableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:39
 * @Created by xuejiaming
 */
public class SelectContext<T1,TR> {
    private int skip;
    private int take;

    private final List<TableInfo> tableInfos;
    private final Class<TR> resultClass;
    private final List<Class<?>> joinTables;

    private final StringBuilder where;

    public SelectContext(Class<T1> mainClass,Class<TR> resultClass){
        tableInfos=new ArrayList<>();
        joinTables=new ArrayList<>();
        tableInfos.add(new TableInfo(mainClass));
        this.resultClass=resultClass;
        this.where= new StringBuilder();
    }

    public List<TableInfo> getTableInfos() {
        return tableInfos;
    }
    public Class<TR> getResultClass() {
        return resultClass;
    }
    public List<Class<?>> getJoinTables() {
        return joinTables;
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
}
