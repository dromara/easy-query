package com.easy.query.core.sharding.route.table.engine;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.route.ShardingRouteResult;
import com.easy.query.core.sharding.route.abstraction.TableRouteManager;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
    private final TableRouteManager tableRouteManager;

    public DefaultTableRouteEngine(TableRouteManager tableRouteManager) {

        this.tableRouteManager = tableRouteManager;
    }

    @Override
    public ShardingRouteResult route(TableRouteContext tableRouteContext) {
        Map<String/*data source*/, Map<Class<?>/*entity class*/, Set<TableRouteUnit>>> routeMaps = new HashMap<>();
        PrepareParseResult prepareParseResult = tableRouteContext.getPrepareParseResult();
        Set<TableAvailable> shardingTables = prepareParseResult.getShardingTables();
        int tableRouteUnitSize = 0;
        boolean onlyShardingDataSource = true;
        for (TableAvailable shardingTable : shardingTables) {
            EntityMetadata entityMetadata = shardingTable.getEntityMetadata();
            if (!entityMetadata.isMultiTableMapping()) {
                continue;
            }
            onlyShardingDataSource = false;
            Collection<TableRouteUnit> shardingRouteUnits = getEntityRouteUnit(tableRouteContext.getDataSourceRouteResult(), shardingTable, prepareParseResult);
            for (TableRouteUnit shardingRouteUnit : shardingRouteUnits) {
//                if (Objects.equals(shardingRouteUnit.getLogicTableName(),shardingRouteUnit.getActualTableName())) {
//                    continue;
//                }
                String dataSource = shardingRouteUnit.getDataSource();
                Map<Class<?>, Set<TableRouteUnit>> tableNamMaps = routeMaps.computeIfAbsent(dataSource, o -> new HashMap<>());
                Set<TableRouteUnit> tableRouteUnits = tableNamMaps.computeIfAbsent(shardingTable.getEntityClass(), o -> new HashSet<>());
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
                throw new EasyQueryInvalidOperationException("");
//                List<RouteMapper> routeMappers = ArrayUtil.select(shardingEntities, (o, i) -> {
//                    EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(o);
//                    return new RouteMapper(o,entityMetadata.getTableName(), entityMetadata.getTableName());
//                });
//                routeUnits.add(new RouteUnit(dataSourceName, routeMappers));
            }

        }
        boolean sequenceQuery=false;
        if (prepareParseResult instanceof QueryPrepareParseResult) {
            QueryPrepareParseResult queryPrepareParseResult = (QueryPrepareParseResult) prepareParseResult;


            //先进行顺序重排,可以让按顺序执行的提高性能,比如按时间分片,并且是时间倒序,那么重排后可以减少很多查询
            SequenceParseResult sequenceOrderPrepareParseResult = queryPrepareParseResult.getSequenceParseResult();
            if (sequenceOrderPrepareParseResult != null) {
                TableAvailable table = sequenceOrderPrepareParseResult.getTable();
                if (EasyCollectionUtil.isNotEmpty(routeUnits)) {
                    RouteUnit first = EasyCollectionUtil.first(routeUnits);
                    int i=getCompareRouteUnitIndex(first,table);
                    if(i>=0){
                        Comparator<String> tableComparator = sequenceOrderPrepareParseResult.getTableComparator();
                        int compareFactor = sequenceOrderPrepareParseResult.isReverse() ? -1 : 1;
                        routeUnits.sort((c1, c2) -> tableComparator.compare(c1.getTableRouteUnits().get(i).getActualTableName(), c2.getTableRouteUnits().get(i).getActualTableName()) * compareFactor);
                        sequenceQuery=true;
                    }
                }
            }
        }

        return new ShardingRouteResult(routeUnits, dataSourceCount > 1, isCrossTable,sequenceQuery);
    }

    /**
     * 寻找顺序排序的表的索引在本次路由里面是哪个然后进行排序
     * @param routeUnit
     * @param table
     * @return
     */
    private int getCompareRouteUnitIndex(RouteUnit routeUnit,TableAvailable table){

        int i = -1;
        for (TableRouteUnit tableRouteUnit : routeUnit.getTableRouteUnits()) {
            i++;
            if(tableRouteUnit.getTableIndex()==table.getIndex()){
                return i;
            }
        }
        return -1;
    }

    private Collection<TableRouteUnit> getEntityRouteUnit(DataSourceRouteResult dataSourceRouteResult, TableAvailable table, PrepareParseResult prepareParseResult) {
        return tableRouteManager.routeTo(table, dataSourceRouteResult, prepareParseResult);
    }
}
