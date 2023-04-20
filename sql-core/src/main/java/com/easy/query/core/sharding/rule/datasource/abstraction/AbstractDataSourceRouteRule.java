package com.easy.query.core.sharding.rule.datasource.abstraction;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.enums.ShardingOperatorEnum;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/19 13:02
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDataSourceRouteRule implements DataSourceRouteRule {

    @Override
    public RouteFunction<String> routeFilter(Comparable<?> shardingValue, ShardingOperatorEnum shardingOperator, String propertyName, boolean isMainShardingProperty) {
       if(isMainShardingProperty){
           return getRouteFilter(shardingValue,shardingOperator);
       }
        return getExtraRouteFilter(shardingValue,shardingOperator,propertyName);
    }
    protected abstract RouteFunction<String> getRouteFilter(Comparable<?> shardingValue,ShardingOperatorEnum shardingOperator);
    protected abstract RouteFunction<String> getExtraRouteFilter(Comparable<?> shardingValue,ShardingOperatorEnum shardingOperator,String propertyName);

    @Override
    public Collection<String> beforeFilterDataSource(Collection<String> allDataSources) {
        return allDataSources;
    }

    @Override
    public Collection<String> afterFilterDataSource(Collection<String> allDataSources, Collection<String> beforeDataSources, Collection<String> filterDataSources) {
        return filterDataSources;
    }
}
