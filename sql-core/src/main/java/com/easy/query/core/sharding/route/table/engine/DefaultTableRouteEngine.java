package com.easy.query.core.sharding.route.table.engine;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.route.ShardingRouteResult;
import com.easy.query.core.sharding.route.abstraction.TableRouteManager;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create time 2023/4/12 16:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableRouteEngine implements TableRouteEngine {
    private final EntityMetadataManager entityMetadataManager;
    private final TableRouteManager tableRouteManager;

    public DefaultTableRouteEngine(EntityMetadataManager entityMetadataManager, TableRouteManager tableRouteManager) {

        this.entityMetadataManager = entityMetadataManager;
        this.tableRouteManager = tableRouteManager;
    }

    @Override
    public ShardingRouteResult route(TableRouteContext tableRouteContext) {
        Map<String/*data source*/, Map<Class<?>/*entity class*/, Set<TableRouteUnit>>> routeMaps = new HashMap<>();
        PrepareParseResult prepareParseResult = tableRouteContext.getPrepareParseResult();
        Set<Class<?>> shardingEntities = prepareParseResult.getShardingEntities();
        int tableRouteUnitSize = 0;
        boolean onlyShardingDataSource = true;
        for (Class<?> shardingEntity : shardingEntities) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(shardingEntity);
            if (!entityMetadata.isMultiTableMapping()) {
                continue;
            }
            onlyShardingDataSource = false;
            Collection<TableRouteUnit> shardingRouteUnits = getEntityRouteUnit(tableRouteContext.getDataSourceRouteResult(), shardingEntity, prepareParseResult);
            for (TableRouteUnit shardingRouteUnit : shardingRouteUnits) {
//                if (Objects.equals(shardingRouteUnit.getLogicTableName(),shardingRouteUnit.getActualTableName())) {
//                    continue;
//                }
                String dataSource = shardingRouteUnit.getDataSource();
                Map<Class<?>, Set<TableRouteUnit>> tableNamMaps = routeMaps.computeIfAbsent(dataSource, o -> new HashMap<>());
                Set<TableRouteUnit> tableRouteUnits = tableNamMaps.computeIfAbsent(shardingEntity, o -> new HashSet<>());
                tableRouteUnits.add(shardingRouteUnit);
                tableRouteUnitSize++;
            }

        }

        //相同的数据源进行笛卡尔积
        //[[ds0,01,a],[ds0,02,a],[ds1,01,a]],[[ds0,01,b],[ds0,03,b],[ds1,01,b]]
        //=>
        //[ds0,[{01,a},{01,b}]],[ds0,[{01,a},{03,b}]],[ds0,[{02,a},{01,b}]],[ds0,[{02,a},{03,b}]],[ds1,[{01,a},{01,b}]]
        //如果笛卡尔积

        List<RouteUnit> routeUnits = new ArrayList<>(tableRouteUnitSize);

        int dataSourceCount = 0;
        boolean isCrossTable = false;
        for (String dataSourceName : tableRouteContext.getDataSourceRouteResult().getIntersectDataSources()) {
            Map<Class<?>, Set<TableRouteUnit>> routeMap = routeMaps.get(dataSourceName);
            if (routeMap != null) {
                Collection<Collection<TableRouteUnit>> cartesian = ArrayUtil.getCartesian(routeMap.values());
                List<TableRouteResult> tableRouteResults = cartesian.stream().map(o -> new TableRouteResult(new ArrayList<>(o)))
                        .filter(o -> !o.isEmpty()).collect(Collectors.toList());

                if(ArrayUtil.isNotEmpty(tableRouteResults)){
                    dataSourceCount++;
                    if(tableRouteResults.size()>1){
                        isCrossTable=true;
                    }
                    for (TableRouteResult tableRouteResult : tableRouteResults) {
                        if(tableRouteResult.getReplaceTables().size()>1){
                            isCrossTable=true;
                        }
//                        List<RouteMapper> routeMappers = ArrayUtil.select(tableRouteResult.getReplaceTables(), (o, i) -> new RouteMapper(o.getEntityClass(),o.getLogicTableName(), o.getActualTableName(),o));
                        routeUnits.add(new RouteUnit(dataSourceName,tableRouteResult.getReplaceTables()));
                    }
                }

            } else if (onlyShardingDataSource) {
                throw new EasyQueryInvalidOperationException("");
//                List<RouteMapper> routeMappers = ArrayUtil.select(shardingEntities, (o, i) -> {
//                    EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(o);
//                    return new RouteMapper(o,entityMetadata.getTableName(), entityMetadata.getTableName());
//                });
//                routeUnits.add(new RouteUnit(dataSourceName, routeMappers));
            }

        }

        return new ShardingRouteResult(routeUnits,dataSourceCount>1,isCrossTable);
    }

    private Collection<TableRouteUnit> getEntityRouteUnit(DataSourceRouteResult dataSourceRouteResult, Class<?> entityClass, PrepareParseResult prepareParseResult) {
        return tableRouteManager.routeTo(entityClass, dataSourceRouteResult, prepareParseResult);
    }
}
