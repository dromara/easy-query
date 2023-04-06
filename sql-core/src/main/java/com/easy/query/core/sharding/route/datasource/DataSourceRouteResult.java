package com.easy.query.core.sharding.route.datasource;

import java.util.Set;

/**
 * create time 2023/4/5 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DataSourceRouteResult {
    private final Set<String> intersectDataSources;

    public DataSourceRouteResult(Set<String> intersectDataSources){

        this.intersectDataSources = intersectDataSources;
    }

    public Set<String> getIntersectDataSources() {
        return intersectDataSources;
    }

    @Override
    public String toString() {
        return "DataSourceRouteResult{" +
                "intersectDataSources=" + intersectDataSources +
                '}';
    }
}
