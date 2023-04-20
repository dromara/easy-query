package com.easy.query.core.sharding.route.table.abstraction;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/18 23:20
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractFilterTableRoute extends AbstractTableRoute{

    public AbstractFilterTableRoute(EntityMetadataManager entityMetadataManager) {
        super(entityMetadataManager);
    }

    @Override
    public Collection<TableRouteUnit> route(TableRouteRule tableRouteRule,DataSourceRouteResult dataSourceRouteResult, SqlParserResult sqlParserResult) {
        Class<?> entityClass = tableRouteRule.entityClass();
        EntityMetadata entityMetadata = getEntityMetadataManager().getEntityMetadata(entityClass);
        Collection<String> tableNames = entityMetadata.getTableNames();
        Collection<String> beforeFilterTableNames = tableRouteRule.beforeFilterTableName(tableNames);
        Collection<TableRouteUnit> tableRouteUnits = route0(tableRouteRule,entityMetadata,dataSourceRouteResult, beforeFilterTableNames, sqlParserResult);
        return  tableRouteRule.afterFilterTableName(tableNames,beforeFilterTableNames,tableRouteUnits);

    }
    public abstract Collection<TableRouteUnit> route0(TableRouteRule tableRouteRule,EntityMetadata entityMetadata,DataSourceRouteResult dataSourceRouteResult,Collection<String> beforeTableNames, SqlParserResult sqlParserResult);
}
