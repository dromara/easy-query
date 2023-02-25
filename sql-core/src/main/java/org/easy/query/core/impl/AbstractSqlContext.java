package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractSqlContext.java
 * @Description: 文件说明
 * @Date: 2023/2/23 21:57
 * @Created by xuejiaming
 */
public abstract class AbstractSqlContext implements SqlContext{
    protected final EasyQueryRuntimeContext runtimeContext;
    protected final List<SqlTableInfo> tables;

    public AbstractSqlContext(EasyQueryRuntimeContext runtimeContext){
        this.runtimeContext = runtimeContext;
        this.tables=new ArrayList<>();
    }

    @Override
    public List<SqlTableInfo> getTables() {
        return tables;
    }

    @Override
    public SqlTableInfo getTable(int index) {
        return tables.get(index);
    }

    @Override
    public String getQuoteName(String value) {
        return runtimeContext.getEasyQueryConfiguration().getDialect().getQuoteName(value);
    }

    @Override
    public String getSqlColumnSegment(int tableIndex,String columnName){
        SqlTableInfo table = getTable(tableIndex);
        String alias = table.getAlias();
        String quoteName = getQuoteName(columnName);
        if(alias==null){
            return quoteName;
        }else{
            return alias+"."+quoteName;
        }
    }
    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
