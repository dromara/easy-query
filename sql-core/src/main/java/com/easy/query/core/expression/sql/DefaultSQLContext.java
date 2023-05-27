package com.easy.query.core.expression.sql;

import com.easy.query.core.context.QueryRuntimeContext;

/**
 * create time 2023/5/27 08:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLContext implements SQLContext{

    private final TableContext tableContext;
    private final QueryRuntimeContext queryRuntimeContext;
    private boolean sharding;
    private boolean subQuery;

    public DefaultSQLContext(QueryRuntimeContext queryRuntimeContext){
        this.queryRuntimeContext = queryRuntimeContext;
        this.tableContext=new TableContext();
        this.sharding=false;
        this.subQuery=false;
    }

    public TableContext getTableContext() {
        return tableContext;
    }

    public QueryRuntimeContext getQueryRuntimeContext() {
        return queryRuntimeContext;
    }

    @Override
    public void useSharding() {
        this.sharding=true;
    }

    @Override
    public boolean isSharding() {
        return sharding;
    }

    @Override
    public boolean hasSubQuery() {
        return subQuery;
    }

    @Override
    public void addSubQuery() {
        this.subQuery=true;
    }
}
