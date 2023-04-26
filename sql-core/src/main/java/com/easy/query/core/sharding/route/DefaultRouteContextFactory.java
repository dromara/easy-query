package com.easy.query.core.sharding.route;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.DefaultEntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.EntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.PredicatePrepareParseResult;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteEngine;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.EasyEntityTableRouteUnit;
import com.easy.query.core.sharding.route.table.EntityTableRouteUnit;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.route.table.engine.TableRouteContext;
import com.easy.query.core.sharding.route.table.engine.TableRouteEngine;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.ClassUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/20 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRouteContextFactory implements RouteContextFactory {
    private final DataSourceRouteEngine dataSourceRouteEngine;
    private final TableRouteEngine tableRouteEngine;

    public DefaultRouteContextFactory(DataSourceRouteEngine dataSourceRouteEngine, TableRouteEngine tableRouteEngine) {

        this.dataSourceRouteEngine = dataSourceRouteEngine;
        this.tableRouteEngine = tableRouteEngine;
    }

    @Override
    public RouteContext createRouteContext(PrepareParseResult prepareParseResult) {
        if (prepareParseResult instanceof PredicatePrepareParseResult) {
            return createRouteContextByPredicate((PredicatePrepareParseResult) prepareParseResult);
        }
        if (prepareParseResult instanceof EntityPrepareParseResult) {
            return createRouteContextByEntity((EntityPrepareParseResult) prepareParseResult);
        }
        throw new UnsupportedOperationException(ClassUtil.getInstanceSimpleName(prepareParseResult));
    }

    private RouteContext createRouteContextByPredicate(PredicatePrepareParseResult prepareParseResult) {
        return doCreateRouteContext(prepareParseResult);
    }

    private RouteContext createRouteContextByEntity(EntityPrepareParseResult prepareParseResult) {
        List<Object> entities = prepareParseResult.getEntities();
        ArrayList<RouteUnit> entityRouteUnits = new ArrayList<>(entities.size());
        String dataSource = null;
        String tableName = null;
        boolean isCrossDataSource = false;
        boolean isCrossTable = false;
        for (Object entity : entities) {
            RouteContext routeContext = doCreateRouteContext(new DefaultEntityPrepareParseResult(prepareParseResult.getShardingEntities(), prepareParseResult.getEntityExpressionBuilder(), Collections.singletonList(entity)));
            List<RouteUnit> routeUnits = routeContext.getEntityRouteResult().getRouteUnits();
            if (ArrayUtil.isNotSingle(routeUnits)) {
                throw new EasyQueryInvalidOperationException("entity route route unit more or empty:"+routeUnits.size());
            }
            RouteUnit routeUnit = routeUnits.get(0);
            List<TableRouteUnit> tableRouteUnits = routeUnit.getTableRouteUnits();

            if (ArrayUtil.isNotSingle(tableRouteUnits)) {
                throw new EasyQueryInvalidOperationException("entity route table route unit more or empty:"+tableRouteUnits.size());
            }
            TableRouteUnit tableRouteUnit = tableRouteUnits.get(0);
            //判断是否存在跨datasource或者跨表的操作
            if (ArrayUtil.isEmpty(entityRouteUnits)) {
                dataSource = routeUnit.getDataSource();
                tableName = routeUnit.getTableRouteUnits().get(0).getActualTableName();
            }
            String currentDataSource = routeUnit.getDataSource();
            String currentTableName = tableRouteUnit.getActualTableName();
            if (!isCrossDataSource) {
                isCrossDataSource = !Objects.equals(dataSource, currentDataSource);
            }
            if (!isCrossTable) {
                isCrossTable = !Objects.equals(tableName, currentTableName);
            }
            EasyEntityTableRouteUnit easyEntityTableRouteUnit = new EasyEntityTableRouteUnit(tableRouteUnit, entity);
            RouteUnit entityRouteUint = new RouteUnit(currentDataSource, Collections.singletonList(easyEntityTableRouteUnit));
            entityRouteUnits.add(entityRouteUint);
        }
        ShardingRouteResult shardingRouteResult = new ShardingRouteResult(entityRouteUnits, isCrossDataSource, isCrossTable);
        return new RouteContext(shardingRouteResult);

    }

    private RouteContext doCreateRouteContext(PrepareParseResult prepareParseResult) {
        //获取分库节点
        DataSourceRouteResult dataSourceRouteResult = dataSourceRouteEngine.route(prepareParseResult);

        //获取分片后的结果
        ShardingRouteResult shardingRouteResult = tableRouteEngine.route(new TableRouteContext(dataSourceRouteResult, prepareParseResult));
//        tableRouteEngine.route()
        return new RouteContext(shardingRouteResult);
    }
}
