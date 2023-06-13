package com.easy.query.core.sharding.route.datasource.abstraction;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * create time 2023/4/19 13:02
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDataSourceRoute<T> implements DataSourceRoute<T> {

    private final Class<T> clazz;
    public AbstractDataSourceRoute(){
        this.clazz=(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    @Override
    public Class<?> entityClass() {
        return clazz;
    }

    @Override
    public RouteFunction<String> routeFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName, boolean isMainShardingProperty, boolean withEntity) {
        if (isMainShardingProperty) {
            return getRouteFilter(table, shardingValue, shardingOperator, withEntity);
        }
        return getExtraRouteFilter(table, shardingValue, shardingOperator, propertyName);
    }

    /**
     * <blockquote><pre>
     *     {@code
     * int i = shardingValue.toString().hashCode();
     *
     *         int dsNumber = Math.abs(i % 4);
     *
     *         String dataSource = "ds" + dsNumber;
     *         switch (shardingOperator){
     *             case EQUAL:
     *                 return ds->dataSource.compareToIgnoreCase(ds)==0;
     *             default:return t->true;
     *         }
     *     }
     * </pre></blockquote>
     *
     * @param table
     * @param shardingValue
     * @param shardingOperator
     * @param withEntity
     * @return
     */
    protected abstract RouteFunction<String> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity);

    protected RouteFunction<String> getExtraRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName) {
        throw new UnsupportedOperationException(propertyName + " sharding route filter");
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
