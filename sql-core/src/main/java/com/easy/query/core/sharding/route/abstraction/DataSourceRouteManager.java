package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.sharding.route.datasource.DataSourceRoute;

import java.util.List;

/**
 * create time 2023/4/5 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteManager {
    List<String> routeTo(Class<?> entityClass,DataSourceRouteParams dataSourceRouteParams);
    DataSourceRoute getRoute(Class<?> entityClass);
}
