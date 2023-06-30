package com.easy.query.core.sharding.router.table.engine;

import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.router.RouteUnit;
import com.easy.query.core.sharding.router.ShardingRouteResult;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptorFactory;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.core.sharding.router.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.router.table.BaseTableRouteUnit;
import com.easy.query.core.sharding.router.table.TableRouteUnit;
import com.easy.query.core.sharding.router.table.TableUnit;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyMapUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create time 2023/4/12 16:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableRouteEngine implements TableRouteEngine {
    private final TableRouteManager tableRouteManager;
    private final RouteDescriptorFactory routeDescriptorFactory;

    public DefaultTableRouteEngine(TableRouteManager tableRouteManager, RouteDescriptorFactory routeDescriptorFactory) {

        this.tableRouteManager = tableRouteManager;
        this.routeDescriptorFactory = routeDescriptorFactory;
    }

    @Override
    public ShardingRouteResult route(TableRouteContext tableRouteContext) {
        Map<String/*data source*/, Map<TableAvailable, Set<TableRouteUnit>>> routeMaps = new HashMap<>();
        TableParseDescriptor tableParseDescriptor = tableRouteContext.getTableParseDescriptor();
        Set<TableAvailable> shardingTables = tableParseDescriptor.getTables();
        int tableRouteUnitSize = 0;
        boolean onlyShardingDataSource = true;
        for (TableAvailable shardingTable : shardingTables) {
            EntityMetadata entityMetadata = shardingTable.getEntityMetadata();
            if (!entityMetadata.isMultiTableMapping()) {
                continue;
            }
            onlyShardingDataSource = false;
            RouteDescriptor routeDescriptor = routeDescriptorFactory.createRouteDescriptor(shardingTable, tableParseDescriptor);
            Collection<TableRouteUnit> shardingRouteUnits = getEntityRouteUnit(tableRouteContext.getDataSourceRouteResult(), routeDescriptor);
            for (TableRouteUnit shardingRouteUnit : shardingRouteUnits) {
//                if (Objects.equals(shardingRouteUnit.getLogicTableName(),shardingRouteUnit.getActualTableName())) {
//                    continue;
//                }
                String dataSource = shardingRouteUnit.getDataSourceName();
                Map<TableAvailable, Set<TableRouteUnit>> tableNamMaps = EasyMapUtil.computeIfAbsent(routeMaps,dataSource, o -> new HashMap<>());
                Set<TableRouteUnit> tableRouteUnits = EasyMapUtil.computeIfAbsent(tableNamMaps,shardingTable, o -> new HashSet<>());
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
            Map<TableAvailable, Set<TableRouteUnit>> routeMap = routeMaps.get(dataSourceName);
            if (routeMap != null) {
                Collection<Collection<TableRouteUnit>> cartesian = EasyCollectionUtil.getCartesian(routeMap.values());
                List<TableRouteResult> tableRouteResults = cartesian.stream().map(o -> new TableRouteResult(new ArrayList<>(o)))
                        .filter(o -> !o.isEmpty()).collect(Collectors.toList());

                if (EasyCollectionUtil.isNotEmpty(tableRouteResults)) {
                    dataSourceCount++;
                    if (tableRouteResults.size() > 1) {
                        isCrossTable = true;
                    }
                    for (TableRouteResult tableRouteResult : tableRouteResults) {
                        if (tableRouteResult.getReplaceTables().size() > 1) {
                            isCrossTable = true;
                        }
//                        List<RouteMapper> routeMappers = ArrayUtil.select(tableRouteResult.getReplaceTables(), (o, i) -> new RouteMapper(o.getEntityClass(),o.getLogicTableName(), o.getActualTableName(),o));
                        routeUnits.add(new RouteUnit(dataSourceName, tableRouteResult.getReplaceTables()));
                    }
                }

            } else if (onlyShardingDataSource) {
                ArrayList<TableRouteUnit> tableRouteUnits = new ArrayList<>(shardingTables.size());
                for (TableAvailable shardingTable : shardingTables) {
                    BaseTableRouteUnit baseTableRouteUnit = new BaseTableRouteUnit(dataSourceName, shardingTable.getTableName(), shardingTable);
                    tableRouteUnits.add(baseTableRouteUnit);
                }
                RouteUnit routeUnit = new RouteUnit(dataSourceName, tableRouteUnits);
                routeUnits.add(routeUnit);
//                List<RouteMapper> routeMappers = ArrayUtil.select(shardingEntities, (o, i) -> {
//                    EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(o);
//                    return new RouteMapper(o,entityMetadata.getTableName(), entityMetadata.getTableName());
//                });
//                routeUnits.add(new RouteUnit(dataSourceName, routeMappers));
            }

        }
        boolean sequenceQuery = false;
        SequenceParseResult sequenceOrderPrepareParseResult = tableRouteContext.getSequenceParseResult();
        if (sequenceOrderPrepareParseResult != null) {

            //先进行顺序重排,可以让按顺序执行的提高性能,比如按时间分片,并且是时间倒序,那么重排后可以减少很多查询
            TableAvailable table = sequenceOrderPrepareParseResult.getTable();
            if (EasyCollectionUtil.isNotEmpty(routeUnits)) {
                RouteUnit first = EasyCollectionUtil.first(routeUnits);
                int i = getCompareRouteUnitIndex(first, table);
                if (i >= 0) {
                    Comparator<TableUnit> tableComparator = sequenceOrderPrepareParseResult.getTableComparator();
                    int compareFactor = sequenceOrderPrepareParseResult.isReverse() ? -1 : 1;
                    routeUnits.sort((c1, c2) -> {
                        TableRouteUnit tableRouteUnit1 = c1.getTableRouteUnits().get(i);
                        TableRouteUnit tableRouteUnit2 = c2.getTableRouteUnits().get(i);
                        return compareFactor * tableComparator.compare(tableRouteUnit1, tableRouteUnit2);
                    });
                    sequenceQuery = true;
                }
            }
        }

        return new ShardingRouteResult(routeUnits, dataSourceCount > 1, isCrossTable, sequenceQuery);
    }

    /**
     * 寻找顺序排序的表的索引在本次路由里面是哪个然后进行排序
     *
     * @param routeUnit
     * @param table
     * @return
     */
    private int getCompareRouteUnitIndex(RouteUnit routeUnit, TableAvailable table) {

        int i = -1;
        for (TableRouteUnit tableRouteUnit : routeUnit.getTableRouteUnits()) {
            i++;
            if (Objects.equals(tableRouteUnit.getTable(),table)) {
                return i;
            }
        }
        return -1;
    }

    private Collection<TableRouteUnit> getEntityRouteUnit(DataSourceRouteResult dataSourceRouteResult, RouteDescriptor routeDescriptor) {
        return tableRouteManager.routeTo(dataSourceRouteResult, routeDescriptor);
    }
}
