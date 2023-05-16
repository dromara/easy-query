package com.easy.query.core.sharding.route.table.abstraction;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.TableRoute;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/18 23:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractTableRoute implements TableRoute {


    @Override
    public <T> Collection<TableRouteUnit> route(TableRouteRule<T> tableRouteRule, DataSourceRouteResult dataSourceRouteResult, TableAvailable table, PrepareParseResult prepareParseResult) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        Collection<ActualTable> actualTables = entityMetadata.getActualTables();
        Collection<ActualTable> beforeFilterTableNames = tableRouteRule.beforeFilterTableName(actualTables);
        Collection<TableRouteUnit> tableRouteUnits = route0(tableRouteRule,table,dataSourceRouteResult, beforeFilterTableNames, prepareParseResult);
        return  tableRouteRule.afterFilterTableName(actualTables,beforeFilterTableNames,tableRouteUnits);

    }
    public abstract <T> Collection<TableRouteUnit> route0(TableRouteRule<T> tableRouteRule, TableAvailable table, DataSourceRouteResult dataSourceRouteResult, Collection<ActualTable> beforeTableNames, PrepareParseResult prepareParseResult);

}
