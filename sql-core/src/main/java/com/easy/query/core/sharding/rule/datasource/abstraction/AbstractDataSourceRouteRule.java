package com.easy.query.core.sharding.rule.datasource.abstraction;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * create time 2023/4/19 13:02
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDataSourceRouteRule<T> implements DataSourceRouteRule<T> {

    private final Class<T> clazz;
    public AbstractDataSourceRouteRule(){
        this.clazz=(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    @Override
    public Class<?> entityClass() {
        return clazz;
    }
    @Override
    public RouteFunction<String> routeFilter(Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName, boolean isMainShardingProperty,boolean withEntity) {
       if(isMainShardingProperty){
           return getRouteFilter(shardingValue,shardingOperator,withEntity);
       }
        return getExtraRouteFilter(shardingValue,shardingOperator,propertyName);
    }
    protected abstract RouteFunction<String> getRouteFilter(Object shardingValue,ShardingOperatorEnum shardingOperator,boolean withEntity);
    protected RouteFunction<String> getExtraRouteFilter(Object shardingValue,ShardingOperatorEnum shardingOperator,String propertyName){
        throw new UnsupportedOperationException(propertyName+" sharding route filter");
    }

    @Override
    public Collection<String> beforeFilterDataSource(Collection<String> allDataSources) {
        return allDataSources;
    }

    @Override
    public Collection<String> afterFilterDataSource(Collection<String> allDataSources, Collection<String> beforeDataSources, Collection<String> filterDataSources) {
        return filterDataSources;
    }
}
