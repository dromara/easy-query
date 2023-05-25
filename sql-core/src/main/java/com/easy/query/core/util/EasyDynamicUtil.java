package com.easy.query.core.util;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.ShardingDataSource;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.limit.MultiConnectionLimit;

import javax.sql.DataSource;

/**
 * create time 2023/5/25 15:16
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyDynamicUtil {
    private EasyDynamicUtil(){}

    /**
     * 动态添加数据源
     * @param runtimeContext
     * @param shardingDataSource
     */
    public static void addDataSource(QueryRuntimeContext runtimeContext, ShardingDataSource shardingDataSource){
        MultiConnectionLimit multiConnectionLimit = runtimeContext.getMultiConnectionLimit();
        EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
        String dataSourceName = shardingDataSource.getDataSourceName();
        DataSource dataSource = shardingDataSource.getDataSource();
        multiConnectionLimit.addThrottlers(dataSourceName,easyQueryOption.getMaxShardingQueryLimit(),shardingDataSource.getDataSourcePoolSize());
        EasyQueryDataSource easyQueryDataSource = runtimeContext.getEasyQueryDataSource();
        easyQueryDataSource.addDataSource(dataSourceName,dataSource);
    }
    public static void addShardingEntity(QueryRuntimeContext runtimeContext, Class<?> entityClass, String dataSourceName, String actualTableName){
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        if(EasyStringUtil.isBlank(entityMetadata.getTableName())){
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass)+" is not table entity");
        }
        if(!entityMetadata.isSharding()){
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass)+" is not sharding entity");
        }
        entityMetadata.addActualTableWithDataSource(dataSourceName,actualTableName);
    }
}
