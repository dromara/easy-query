package com.easy.query.search;

import com.easy.query.core.api.dynamic.executor.query.WhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.sort.ObjectSortQueryExecutor;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.search.executor.DefaultEasySearchQueryExecutor;
import com.easy.query.search.executor.EasySearchQueryExecutor;
import com.easy.query.search.meta.DefaultEasySearchMetaDataManager;
import com.easy.query.search.meta.EasySearchMetaDataManager;

/**
 * create time 2025/6/25 21:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class SearchInjectConfiguration {
    public static ServiceCollection configure(ServiceCollection services) {

        services.addService(WhereObjectQueryExecutor.class, EasySearchWhereObjectQueryExecutor.class);
        services.addService(ObjectSortQueryExecutor.class, EasySearchObjectSortQueryExecutor.class);

        services.addService(EasySearchMetaDataManager.class, DefaultEasySearchMetaDataManager.class);
        services.addService(EasySearchQueryExecutor.class, DefaultEasySearchQueryExecutor.class);
        services.addService(EasySearchConfigurationProvider.class, DefaultEasySearchConfigurationProvider.class);
        return services;
    }

}
