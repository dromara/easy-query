package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation.AggregationUnitFactory;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.AggregateValue;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.GroupByRowComparator;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.GroupValue;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row.DefaultMemoryResultSetRow;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row.MemoryResultSetRow;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.context.ColumnIndexFuncColumnSegment;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2023/5/2 23:45
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyInMemoryGroupByOrderStreamMergeResultSet extends AbstractInMemoryStreamMergeResultSet {
    private static final Log log= LogFactory.getLog(EasyInMemoryGroupByOrderStreamMergeResultSet.class);
    public EasyInMemoryGroupByOrderStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        super(streamMergeContext, streamResultSets);

    }

    @Override
    protected List<MemoryResultSetRow> init(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        Map<GroupValue, MemoryResultSetRow> dataMap = new LinkedHashMap<>(1024);
       Map<GroupValue, List<AggregateValue>> aggregationMap = new LinkedHashMap<>(1024);

        for (StreamResultSet resultSet : streamResultSets) {
            try(StreamResultSet streamResultSet=resultSet) {
                while(streamResultSet.next()){
                    GroupValue groupValue = new GroupValue(streamMergeContext, streamResultSet);
                    initForFirstGroupByValue(streamMergeContext,streamResultSet,groupValue,dataMap,aggregationMap);
                    aggregate(streamResultSet,groupValue,aggregationMap);
                }

            } catch (Exception e) {
                log.error("init memory merge result set error.",e);
                throw new SQLException(e);
            }
        }
        setAggregationValueToMemoryRow(streamMergeContext,dataMap,aggregationMap);

        List<Boolean> valueCaseSensitive = EasyCollectionUtil.isEmpty(streamResultSets) ? Collections.emptyList() : getValueCaseSensitive();
        return getMemoryResultSetRows(streamMergeContext,dataMap,valueCaseSensitive);

    }
    private List<MemoryResultSetRow> getMemoryResultSetRows(StreamMergeContext streamMergeContext,Map<GroupValue, MemoryResultSetRow> dataMap,List<Boolean> valueCaseSensitive){
        ArrayList<MemoryResultSetRow> resultSetRows = new ArrayList<>(dataMap.values());
        resultSetRows.sort(new GroupByRowComparator(streamMergeContext,valueCaseSensitive));
        return resultSetRows;
    }

    public List<Boolean> getValueCaseSensitive() throws SQLException {
        ArrayList<Boolean> result = new ArrayList<>(columnCount);
        for (int i = 0; i < columnCount; i++) {
            result.add(metaData.isCaseSensitive(i+1));
        }
        return result;
    }

    private void initForFirstGroupByValue(StreamMergeContext streamMergeContext,StreamResultSet streamResultSet,GroupValue groupValue,
                                          Map<GroupValue, MemoryResultSetRow> dataMap,Map<GroupValue, List<AggregateValue>> aggregationMap) throws SQLException {
        if(!dataMap.containsKey(groupValue)){
            dataMap.put(groupValue,new DefaultMemoryResultSetRow(streamResultSet,columnCount));
        }
        if(!aggregationMap.containsKey(groupValue)){
            //todo 优化是否需要每次创建
            List<AggregateValue> aggregationUnitValues = createAggregationUnitValues();
            aggregationMap.put(groupValue,aggregationUnitValues);
        }
    }
    private void aggregate(StreamResultSet streamResultSet,GroupValue groupValue,
                           Map<GroupValue, List<AggregateValue>> aggregationMap) throws SQLException {
        List<AggregateValue> aggregateValues = aggregationMap.get(groupValue);
        for (AggregateValue aggregationValue : aggregateValues) {
            ArrayList<Comparable<?>> comparables = new ArrayList<>(2);
            if(EasyCollectionUtil.isEmpty(aggregationValue.getAggregateValues())){

                Comparable<?> value = getAggregationValue(streamResultSet,aggregationValue.getColumnIndex());
                comparables.add(value);
            }else{
                for (AggregateValue aggregateValue : aggregationValue.getAggregateValues()) {
                    Comparable<?> value = getAggregationValue(streamResultSet,aggregateValue.getColumnIndex());
                    comparables.add(value);
                }
            }
            aggregationValue.getAggregationUnit().merge(comparables);
        }
    }

    private Comparable<?> getAggregationValue(StreamResultSet streamResultSet,int columnIndex) throws SQLException {
        Object result = streamResultSet.getObject(columnIndex + 1);
        if (null == result || result instanceof Comparable) {
            return (Comparable<?>) result;
        }
        throw new EasyQuerySQLCommandException("aggregation value must implements comparable");
    }
    private void setAggregationValueToMemoryRow(StreamMergeContext streamMergeContext,Map<GroupValue, MemoryResultSetRow> dataMap,Map<GroupValue, List<AggregateValue>> aggregationMap) throws SQLException {
        for (Map.Entry<GroupValue, MemoryResultSetRow> dataKv : dataMap.entrySet()) {
            List<AggregateValue> aggregateValues = aggregationMap.get(dataKv.getKey());
            for (AggregateValue aggregateValue : aggregateValues) {
                dataKv.getValue().setValue(aggregateValue.getColumnIndex()+1,aggregateValue.getAggregationUnit().getResult());
            }
        }
    }
    private List<AggregateValue> createAggregationUnitValues() {
        List<SQLSegment> sqlSegments = streamMergeContext.getSelectColumns().getSQLSegments();
        ArrayList<AggregateValue> aggregationUnits = new ArrayList<>(columnCount);
        for (int i = 0; i < sqlSegments.size(); i++) {
            SQLSegment sqlSegment = sqlSegments.get(i);

            boolean aggregateColumn = EasySQLSegmentUtil.isAggregateColumn(sqlSegment);
            if(aggregateColumn){
                FuncColumnSegment maybeAggregateColumnSegment = (FuncColumnSegment) sqlSegment;
                AggregateValue aggregateValue = new AggregateValue(i, AggregationUnitFactory.create(maybeAggregateColumnSegment.getAggregationType()));
                if(Objects.equals(AggregationType.AVG,maybeAggregateColumnSegment.getAggregationType())){
                    Map<AggregationType, ColumnIndexFuncColumnSegment> aggregationTypeFuncColumnSegmentMap = streamMergeContext.getGroupMergeContext().getColumnMapping().get(maybeAggregateColumnSegment);
                    if(aggregationTypeFuncColumnSegmentMap==null){
                        throw new UnsupportedOperationException("not found sum or count projects, avg column:" + EasyClassUtil.getInstanceSimpleName(sqlSegment));
                    }
                    ColumnIndexFuncColumnSegment countAvgColumnSegment = aggregationTypeFuncColumnSegmentMap.get(AggregationType.COUNT);
                    if(countAvgColumnSegment==null){
                        throw new UnsupportedOperationException("not found count projects, avg column:" + EasyClassUtil.getInstanceSimpleName(sqlSegment));
                    }
                    AggregateValue avgCountAggregateValue = new AggregateValue(countAvgColumnSegment.getColumnIndex(), AggregationUnitFactory.create(countAvgColumnSegment.getFuncColumnSegment().getAggregationType()));
                    aggregateValue.addAggregateValue(avgCountAggregateValue);
                    ColumnIndexFuncColumnSegment sumAvgColumnSegment = aggregationTypeFuncColumnSegmentMap.get(AggregationType.SUM);
                    if(sumAvgColumnSegment==null){
                        throw new UnsupportedOperationException("not found sum projects, avg column:" + EasyClassUtil.getInstanceSimpleName(sqlSegment));
                    }
                    AggregateValue sumCountAggregateValue = new AggregateValue(sumAvgColumnSegment.getColumnIndex(), AggregationUnitFactory.create(sumAvgColumnSegment.getFuncColumnSegment().getAggregationType()));
                    aggregateValue.addAggregateValue(sumCountAggregateValue);
                }
                aggregationUnits.add(aggregateValue);

            }
        }
        return aggregationUnits;
    }
}
