package com.easy.query.core.sharding.route.abstraction;

import java.util.List;

/**
 * create time 2023/4/11 13:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDataSourceRouteManager implements DataSourceRouteManager{
    @Override
    public List<String> routeTo(Class<?> entityType, DataSourceRouteParams dataSourceRouteParams) {
        return null;
    }
}
