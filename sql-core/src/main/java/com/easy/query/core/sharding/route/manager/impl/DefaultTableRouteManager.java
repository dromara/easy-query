package com.easy.query.core.sharding.route.manager.impl;

import com.easy.query.core.bean.BeanValueCaller;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.route.manager.TableRouteManager;
import com.easy.query.core.sharding.route.table.ShardingTableRoute;
import com.easy.query.core.sharding.route.table.TableRoute;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.table.TableRouteRule;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/20 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableRouteManager implements TableRouteManager {
    private final Map<Class<?>, TableRouteRule<?>> entityRouteRuleCache= new ConcurrentHashMap<>();
    private final TableRoute tableRoute;
    public DefaultTableRouteManager(BeanValueCaller beanValueCaller){
        tableRoute=new ShardingTableRoute(beanValueCaller);
    }

    @Override
    public Collection<TableRouteUnit> routeTo(DataSourceRouteResult dataSourceRouteResult, RouteDescriptor routeDescriptor) {
        TableRouteRule<?> routeRule = getRouteRule(routeDescriptor.getTable().getEntityClass());
        return tableRoute.route(routeRule,dataSourceRouteResult,routeDescriptor);
//        if(prepareParseResult instanceof QueryPrepareParseResult){
//            return tableRoute.route(routeRule,dataSourceRouteResult,prepareParseResult);
//        }
//        if(prepareParseResult instanceof InsertPrepareParseResult){
//            InsertPrepareParseResult insertPrepareParseResult = (InsertPrepareParseResult) prepareParseResult;
//            List<Object> entities = insertPrepareParseResult.getEntities();
//            Set<Class<?>> shardingEntities = insertPrepareParseResult.getShardingEntities();
//            ArrayList<TableRouteUnit> tableRouteUnits = new ArrayList<>(entities.size());
//            for (Object entity : entities) {
//                PrepareParseResult entityPrepareResult = createEntityPrepareResult(insertPrepareParseResult, entity);
//                Collection<TableRouteUnit> routeTableRouteUnits = tableRoute.route(routeRule, dataSourceRouteResult, entityPrepareResult);
//                if(routeTableRouteUnits.size()<1){
//                    throw new EasyQueryInvalidOperationException("not found table route unit");
//                }if(routeTableRouteUnits.size()>1){
//                    throw new EasyQueryInvalidOperationException("more table route unit found:"+ StringUtil.join(routeTableRouteUnits,o->o.getDataSource()+":"+o.getLogicTableName()+":"+o.getActualTableName(),","));
//                }
//                TableRouteUnit tableRouteUnit = routeTableRouteUnits.iterator().next();
//                tableRouteUnits.add(new EasyInsertTableRouteUnit(tableRouteUnit,entity));
//            }
//            return tableRouteUnits;
//        }
//        throw new UnsupportedOperationException(ClassUtil.getInstanceSimpleName(prepareParseResult));
    }

    @Override
    public TableRouteRule<?> getRouteRule(Class<?> entityClass) {

        TableRouteRule<?> tableRouteRule = entityRouteRuleCache.get(entityClass);
        if(tableRouteRule==null){
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) +" not found table route rule");
        }
        return tableRouteRule;
    }

    @Override
    public boolean addRouteRule(TableRouteRule<?> tableRouteRule) {
        TableRouteRule<?> oldTableRouteRule = entityRouteRuleCache.get(tableRouteRule.entityClass());
        if(oldTableRouteRule==null){
            entityRouteRuleCache.put(tableRouteRule.entityClass(),tableRouteRule);
            return true;
        }
        return false;
    }
}
