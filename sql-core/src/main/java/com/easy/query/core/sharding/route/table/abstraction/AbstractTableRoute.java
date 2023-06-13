package com.easy.query.core.sharding.route.table.abstraction;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.router.table.TableRouteUnit;
import com.easy.query.core.sharding.route.table.TableRoute;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * create time 2023/4/19 09:41
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractTableRoute<T> implements TableRoute<T> {
    private final Class<T> clazz;
    public AbstractTableRoute(){
        this.clazz=(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    @Override
    public Class<?> entityClass() {
        return clazz;
    }

    @Override
    public RouteFunction<ActualTable> routeFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName, boolean isMainShardingProperty, boolean withEntity) {
       if(isMainShardingProperty){
           return getRouteFilter(table,shardingValue,shardingOperator,withEntity);
       }
       return getExtraRouteFilter(table,shardingValue,shardingOperator,propertyName);
    }

    protected abstract RouteFunction<ActualTable> getRouteFilter(TableAvailable table, Object shardingValue,ShardingOperatorEnum shardingOperator,boolean withEntity);
    protected  RouteFunction<ActualTable> getExtraRouteFilter(TableAvailable table, Object shardingValue,ShardingOperatorEnum shardingOperator,String propertyName){
        throw new UnsupportedOperationException(propertyName+" sharding route filter");
    }
    @Override
    public Collection<ActualTable> beforeFilterTableName(Collection<ActualTable> allActualTables) {
        return allActualTables;
    }

    @Override
    public Collection<TableRouteUnit> afterFilterTableName(Collection<ActualTable> allActualTables, Collection<ActualTable> beforeActualTables, Collection<TableRouteUnit> filterRouteUnits) {
        return filterRouteUnits;
    }
}
