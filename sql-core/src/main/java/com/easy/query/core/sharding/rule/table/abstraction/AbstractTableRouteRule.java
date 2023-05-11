package com.easy.query.core.sharding.rule.table.abstraction;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * create time 2023/4/19 09:41
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractTableRouteRule<T> implements TableRouteRule<T> {
    private final Class<T> clazz;
    public AbstractTableRouteRule(){
        this.clazz=(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    @Override
    public Class<?> entityClass() {
        return clazz;
    }

    @Override
    public RouteFunction<String> routeFilter(Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName,boolean isMainShardingProperty,boolean withEntity) {
       if(isMainShardingProperty){
           return getRouteFilter(shardingValue,shardingOperator,withEntity);
       }
       return getExtraRouteFilter(shardingValue,shardingOperator,propertyName);
    }

    protected abstract RouteFunction<String> getRouteFilter(Object shardingValue,ShardingOperatorEnum shardingOperator,boolean withEntity);
    protected  RouteFunction<String> getExtraRouteFilter(Object shardingValue,ShardingOperatorEnum shardingOperator,String propertyName){
        throw new UnsupportedOperationException(propertyName+" sharding route filter");
    }
    @Override
    public Collection<String> beforeFilterTableName(Collection<String> allTableNames) {
        return allTableNames;
    }

    @Override
    public Collection<TableRouteUnit> afterFilterTableName(Collection<String> allTableNames, Collection<String> beforeTableNames, Collection<TableRouteUnit> filterRouteUnits) {
        return filterRouteUnits;
    }
}
