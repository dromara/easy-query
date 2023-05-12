package com.easy.query.core.sharding.route.manager.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.datasource.ShardingDataSourceRoute;
import com.easy.query.core.sharding.route.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;
import com.easy.query.core.sharding.rule.table.TableRouteRule;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/11 13:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDataSourceRouteManager implements DataSourceRouteManager {
    private final Map<Class<?>, DataSourceRouteRule> entityRouteRuleCache= new ConcurrentHashMap<>();
    private final DataSourceRoute dataSourceRoute;
    private final EasyQueryDataSource easyDataSource;

    public DefaultDataSourceRouteManager(EasyQueryDataSource easyDataSource){

        this.easyDataSource = easyDataSource;
        dataSourceRoute=new ShardingDataSourceRoute();
    }
    @Override
    public Collection<String> routeTo(TableAvailable table, PrepareParseResult prepareParseResult) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        if(!entityMetadata.isMultiDataSourceMapping()){
            return Collections.singletonList(easyDataSource.getDefaultDataSourceName());
        }
        DataSourceRouteRule<?> routeRule = getRouteRule(table.getEntityClass());
        return dataSourceRoute.route(routeRule,table, prepareParseResult);
    }

    @Override
    public DataSourceRouteRule<?> getRouteRule(Class<?> entityClass) {
        DataSourceRouteRule<?> dataSourceRouteRule = entityRouteRuleCache.get(entityClass);
        if(dataSourceRouteRule==null){
            throw new EasyQueryInvalidOperationException(ClassUtil.getSimpleName(entityClass) +" not found data source route rule");
        }
        return dataSourceRouteRule;
    }

    @Override
    public boolean addRouteRule(DataSourceRouteRule<?> dataSourceRouteRule) {
        DataSourceRouteRule<?> oldDataSourceRouteRule = entityRouteRuleCache.get(dataSourceRouteRule.entityClass());
        if(oldDataSourceRouteRule==null){
            entityRouteRuleCache.put(dataSourceRouteRule.entityClass(),dataSourceRouteRule);
            return true;
        }
        return false;
    }
}
