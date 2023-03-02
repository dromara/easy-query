package org.easy.query.core.expression.context;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractSqlContext.java
 * @Description: 文件说明
 * @Date: 2023/2/23 21:57
 * @Created by xuejiaming
 */
public abstract class AbstractSqlContext implements SqlContext {
    protected final EasyQueryRuntimeContext runtimeContext;
    protected final List<SqlTableInfo> tables;
    protected final List<SQLParameter> params;

    public AbstractSqlContext(EasyQueryRuntimeContext runtimeContext){
        this.runtimeContext = runtimeContext;
        this.tables=new ArrayList<>();
        this.params=new ArrayList<>();
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
    public String getSqlColumnSegment(SqlTableInfo table,String propertyName){
        String alias = table.getAlias();
        String columnName = table.getColumnName(propertyName);
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

    @Override
    public List<SQLParameter> getParameters() {
        return params;
    }
    @Override
    public void addParameter(SQLParameter parameter) {
        params.add(parameter);
    }

}
