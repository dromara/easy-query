package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.ShardingTableRoute;
import com.easy.query.core.sharding.route.table.TableRoute;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;
import com.easy.query.core.sharding.rule.table.TableRouteRule;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/20 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableRouteManager implements TableRouteManager{
    private final Map<Class<?>, TableRouteRule> entityRouteRuleCache= new ConcurrentHashMap<>();
    private final TableRoute tableRoute;

    public DefaultTableRouteManager(EntityMetadataManager entityMetadataManager){

        this.tableRoute=new ShardingTableRoute(entityMetadataManager);
    }
    @Override
    public Collection<TableRouteUnit> routeTo(Class<?> entityClass, DataSourceRouteResult dataSourceRouteResult, SqlParserResult sqlParserResult) {
        TableRouteRule routeRule = getRouteRule(entityClass);
        return tableRoute.route(routeRule,dataSourceRouteResult,sqlParserResult);
    }

    @Override
    public TableRouteRule getRouteRule(Class<?> entityClass) {

        TableRouteRule tableRouteRule = entityRouteRuleCache.get(entityClass);
        if(tableRouteRule==null){
            throw new EasyQueryInvalidOperationException(ClassUtil.getSimpleName(entityClass) +" not found table route rule");
        }
        return tableRouteRule;
    }

    @Override
    public boolean addRouteRule(TableRouteRule tableRouteRule) {
        TableRouteRule oldTableRouteRule = entityRouteRuleCache.get(tableRouteRule.entityClass());
        if(oldTableRouteRule==null){
            entityRouteRuleCache.put(tableRouteRule.entityClass(),tableRouteRule);
            return true;
        }
        return false;
    }
}
