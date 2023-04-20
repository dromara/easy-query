package com.easy.query.core.sharding.route.datasource.abstraction;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/19 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractFilterDataSourceRoute extends AbstractDataSourceRoute{

    public AbstractFilterDataSourceRoute(EntityMetadataManager entityMetadataManager) {
        super(entityMetadataManager);
    }

    @Override
    public Collection<String> route(DataSourceRouteRule dataSourceRouteRule, SqlParserResult sqlParserResult) {
        Class<?> entityClass = dataSourceRouteRule.entityClass();
        EntityMetadata entityMetadata = getEntityMetadataManager().getEntityMetadata(entityClass);
        Collection<String> dataSources = entityMetadata.getDataSources();
        Collection<String> beforeFilterDataSource = dataSourceRouteRule.beforeFilterDataSource(dataSources);
        Collection<String> filterDataSources = route0(dataSourceRouteRule,entityMetadata, beforeFilterDataSource, sqlParserResult);
        return  dataSourceRouteRule.afterFilterDataSource(dataSources,beforeFilterDataSource,filterDataSources);

    }
    public abstract Collection<String> route0(DataSourceRouteRule dataSourceRouteRule, EntityMetadata entityMetadata, Collection<String> beforeTableNames, SqlParserResult sqlParserResult);

}
