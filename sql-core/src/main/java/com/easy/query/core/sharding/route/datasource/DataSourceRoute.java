package com.easy.query.core.sharding.route.datasource;

import com.easy.query.core.sharding.route.Route;
import com.easy.query.core.sharding.route.RouteFilter;

import java.util.Collection;

/**
 * create time 2023/4/19 13:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRoute<T> extends Route, RouteFilter<String> {
    Collection<String> beforeFilterDataSource(Collection<String> allDataSources);
    Collection<String> afterFilterDataSource(Collection<String> allDataSources, Collection<String> beforeDataSources, Collection<String> filterDataSources);

}
