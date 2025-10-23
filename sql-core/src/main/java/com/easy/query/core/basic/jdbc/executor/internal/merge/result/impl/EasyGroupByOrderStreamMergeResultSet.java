package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation.AggregationUnitFactory;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyGroup;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.context.ColumnIndexFuncColumnSegment;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.ShardingStreamResultSet;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * create time 2023/4/27 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyGroupByOrderStreamMergeResultSet implements ShardingStreamResultSet {
    private static final Log log= LogFactory.getLog(EasyGroupByOrderStreamMergeResultSet.class);
    private final StreamMergeContext streamMergeContext;
    private final List<StreamResultSet> streamResultSets;
    private final Queue<StreamResultSet> queue;
    private final ResultSetMetaData resultSetMetaData;
    private StreamResultSet currentStreamResult;
    private final List<Object> currentRow;
    private final int columnCount;
    //数组下标对应select的下标,值为1的表示他是Group列0表示为func列
    private final int[] selectColumns;
    private List<Object> currentGroupValues;
    private boolean skipFirst;

    private boolean wasNull;
    private boolean closed = false;

    public EasyGroupByOrderStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {

        this.streamMergeContext = streamMergeContext;
        this.streamResultSets = streamResultSets;
        this.queue = new PriorityQueue<>(streamResultSets.size());
        this.skipFirst = true;
        setOrderStreamResult();
        this.resultSetMetaData = getResultSetMetaData();
        this.columnCount = resultSetMetaData.getColumnCount();
        this.currentRow = new ArrayList<>(columnCount);

        List<PropertyGroup> groups = streamMergeContext.getGroups();
        this.selectColumns = new int[streamMergeContext.getSelectColumns().getSQLSegments().size()];
        for (PropertyGroup group : groups) {
            int columnIndex = group.columnIndex();
            if (columnIndex >= 0) {
                selectColumns[columnIndex] = 1;
            }
        }
    }

    private void setOrderStreamResult() throws SQLException {
        for (StreamResultSet streamResult : streamResultSets) {
            EasyOrderStreamMergeResultSet easyOrderStreamMergeResult = new EasyOrderStreamMergeResultSet(streamMergeContext, streamResult);
            if (easyOrderStreamMergeResult.hasElement()) {
                easyOrderStreamMergeResult.skipFirst();
                queue.offer(easyOrderStreamMergeResult);
            }
        }
        currentStreamResult = queue.isEmpty() ? EasyCollectionUtil.firstOrNull(streamResultSets) : queue.peek();
        currentGroupValues = queue.isEmpty() ? Collections.emptyList() : new GroupValue(streamMergeContext, currentStreamResult).getGroupValues();
    }

    private ResultSetMetaData getResultSetMetaData() throws SQLException {
        return currentStreamResult.getMetaData();
    }

    @Override
    public ResultSet getResultSet() {
        throw new EasyQueryInvalidOperationException("this Stream:["+ EasyClassUtil.getSimpleName(this.getClass()) +"] getResultSet not support");
    }

    @Override
    public boolean hasElement() {
        return currentStreamResult.hasElement();
    }

    @Override
    public boolean skipFirst() {
        return skipFirst;
    }

    @Override
    public boolean next() throws SQLException {
        currentRow.clear();
        if (queue.isEmpty()) {
            return false;
        }
        if (skipFirst) {
            next0();
        }
        if (aggregateCurrentGroupByRowAndNext()) {
            currentGroupValues = new GroupValue(streamMergeContext, currentStreamResult).getGroupValues();
        }
        return true;

    }

    private boolean next0() throws SQLException {

        if (queue.isEmpty()) {
            return false;
        }
        if (skipFirst) {
            skipFirst = false;
            return true;
        }
        StreamResultSet first = queue.poll();
        if (first.next()) {
            queue.offer(first);
        }
        if (queue.isEmpty()) {
            return false;
        }
        currentStreamResult = queue.peek();
        return true;
    }


    private boolean aggregateCurrentGroupByRowAndNext() throws SQLException {
        boolean result = false;
        boolean cachedRow = false;
        List<AggregateValue> aggregationValues = createAggregationUnitValues();
        while (currentGroupValues.equals(new GroupValue(streamMergeContext, currentStreamResult).getGroupValues())) {
            aggregate(aggregationValues);
            if (!cachedRow) {
                cacheCurrentRow();
                cachedRow = true;
            }
            result = next0();
            if (!result) {
                break;
            }
        }
        setAggregationValueToCurrentRow(aggregationValues);
        return result;
    }

    private void setAggregationValueToCurrentRow(List<AggregateValue> aggregationValues) {
        for (AggregateValue aggregationValue : aggregationValues) {
            currentRow.set(aggregationValue.getColumnIndex(), aggregationValue.getAggregationUnit().getResult());
        }
    }

    private void aggregate(List<AggregateValue> aggregationValues) throws SQLException {
        for (AggregateValue aggregationValue : aggregationValues) {
            ArrayList<Comparable<?>> comparables = new ArrayList<>(2);
            if(EasyCollectionUtil.isEmpty(aggregationValue.getAggregateValues())){

                Comparable<?> value = getAggregationValue(aggregationValue.getColumnIndex());
                comparables.add(value);
            }else{
                for (AggregateValue aggregateValue : aggregationValue.getAggregateValues()) {
                    Comparable<?> value = getAggregationValue(aggregateValue.getColumnIndex());
                    comparables.add(value);
                }
            }
            aggregationValue.getAggregationUnit().merge(comparables);//判断如果是avg应该要获取对应的count和sum
        }
    }

    private Comparable<?> getAggregationValue(int columnIndex) throws SQLException {
        Object result = currentStreamResult.getObject(columnIndex + 1);
        if (null == result || result instanceof Comparable) {
            return (Comparable<?>) result;
        }
        throw new EasyQuerySQLCommandException("aggregation value must implements comparable");
    }

    private List<AggregateValue> createAggregationUnitValues() {
        List<SQLSegment> sqlSegments = streamMergeContext.getSelectColumns().getSQLSegments();
        ArrayList<AggregateValue> aggregationUnits = new ArrayList<>(columnCount);
        for (int i = 0; i < selectColumns.length; i++) {
            boolean aggregateColumn = selectColumns[i] == 0;
            if (aggregateColumn) {
                SQLSegment sqlSegment = sqlSegments.get(i);
                boolean isAggregateColumn = EasySQLSegmentUtil.isAggregateColumn(sqlSegment);
                if (!isAggregateColumn) {
                    throw new UnsupportedOperationException("unknown aggregate column:" + EasyClassUtil.getInstanceSimpleName(sqlSegment));
                }
                FuncColumnSegment aggregationColumnSegment = (FuncColumnSegment) sqlSegment;
                AggregateValue aggregateValue = new AggregateValue(i, AggregationUnitFactory.create(aggregationColumnSegment.getAggregationType()));
                if(Objects.equals(AggregationType.AVG,aggregationColumnSegment.getAggregationType())){
                    Map<AggregationType, ColumnIndexFuncColumnSegment> aggregationTypeFuncColumnSegmentMap = streamMergeContext.getGroupMergeContext().getColumnMapping().get(aggregationColumnSegment);
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


    private void cacheCurrentRow() throws SQLException {
        for (int i = 0; i < columnCount; i++) {
            currentRow.add(currentStreamResult.getObject(i + 1));
        }
    }

//    private boolean equalWithGroupValues() {
//        List<Object> current = new GroupValue(streamMergeContext, queue.peek()).getGroupValues();
//        for (int i = 0; i < currentGroupValues.size(); i++) {
//            if (!Objects.equals(currentGroupValues.get(i), current.get(i))) {
//                return false;
//            }
//        }
//        return true;
//    }

    private void setWasNull(boolean wasNull) {
        this.wasNull = wasNull;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        return value;
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        return EasyObjectUtil.typeCastNullable(value);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return wasNull;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return resultSetMetaData;
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        return (SQLXML) value;
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if(value instanceof LocalDateTime){
            return Timestamp.valueOf((LocalDateTime)value);
        }
        if(value instanceof LocalDate){

            long ts = ((LocalDate)value).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            return new Timestamp(ts);
        }
        return (Timestamp) value;
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        return (Time) value;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        return (String) value;
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        return (Date) value;
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        if(value instanceof Short){
            return (Short)value;
        }
        return ((Number) value).shortValue();
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        if(value instanceof Long){
            return (Long)value;
        }
        return ((Number) value).longValue();
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        if(value instanceof Integer){
            return (Integer)value;
        }
        return ((Number) value).intValue();
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        if(value instanceof Float){
            return (Float)value;
        }
        return ((Number) value).floatValue();
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        if(value instanceof Double){
            return (Double)value;
        }
        return ((Number) value).doubleValue();
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        return (Clob) value;
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        return (byte) value;
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if (value == null) {
            return new byte[0];
        }
        return (byte[]) value;
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if (value == null) {
            return false;
        }
        return (boolean) value;
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        return (Blob) value;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        Object value = currentRow.get(columnIndex - 1);
        setWasNull(value == null);
        if(value==null){
            return null;
        }
        if(value instanceof BigDecimal){
            return (BigDecimal) value;
        }
        return new BigDecimal(value.toString());
    }

    @Override
    public void close() throws SQLException {
        if (closed) {
            return;
        }
        closed = true;
        currentRow.clear();
        for (StreamResultSet streamResultSet : streamResultSets) {
            try {
                streamResultSet.close();
            }catch (Exception exception){
                log.error("close stream result set error.",exception);
            }
        }
    }
}
