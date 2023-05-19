package com.easy.query.core.sharding.rule.datasource.abstraction;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
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
    public RouteFunction<String> routeFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName, boolean isMainShardingProperty, boolean withEntity) {
        if(isMainShardingProperty){
            return getRouteFilter(table,shardingValue,shardingOperator,withEntity);
        }
        return getExtraRouteFilter(table,shardingValue,shardingOperator,propertyName);
    }
    protected abstract RouteFunction<String> getRouteFilter(TableAvailable table,Object shardingValue,ShardingOperatorEnum shardingOperator,boolean withEntity);
    protected RouteFunction<String> getExtraRouteFilter(TableAvailable table,Object shardingValue,ShardingOperatorEnum shardingOperator,String propertyName){
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
