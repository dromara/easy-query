package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.InMemoryStreamMergeResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row.MemoryResultSetRow;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/5/3 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractInMemoryStreamMergeResultSet implements InMemoryStreamMergeResultSet {
    protected final Iterator<MemoryResultSetRow> memoryResultSetRows;
    protected final List<MemoryResultSetRow> memoryResultSetRowList;
    protected final ResultSetMetaData metaData;
    protected final int columnCount;
    protected final StreamMergeContext streamMergeContext;
    protected final int reallyCount;
    private MemoryResultSetRow currentResultSetRow;
    private boolean wasNull;
    private boolean skipFirst;
    private boolean closed = false;

    public AbstractInMemoryStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        this.streamMergeContext = streamMergeContext;
        this.metaData = streamResultSets.get(0).getMetaData();
        this.columnCount = metaData.getColumnCount();
        this.memoryResultSetRowList = init(streamMergeContext, streamResultSets);
        this.reallyCount = memoryResultSetRowList.size();
        this.memoryResultSetRows = memoryResultSetRowList.iterator();
        if (this.memoryResultSetRows.hasNext()) {
            currentResultSetRow = this.memoryResultSetRows.next();
        }
        this.skipFirst = true;
    }

    protected abstract List<MemoryResultSetRow> init(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException;

    @Override
    public boolean hasElement() {
        return currentResultSetRow != null;
    }

    @Override
    public boolean skipFirst() {
        if (skipFirst) {
            skipFirst = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean next() throws SQLException {

        if (skipFirst) {
            skipFirst = false;
            return currentResultSetRow != null;
        }

        if (this.memoryResultSetRows.hasNext()) {
            this.currentResultSetRow = this.memoryResultSetRows.next();
            return true;
        } else {
            this.currentResultSetRow = null;
        }
        return false;
    }

    private void setWasNull(boolean wasNull) {
        this.wasNull = wasNull;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return value;
    }

    @Override
    public boolean wasNull() throws SQLException {
        return wasNull;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return metaData;
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (SQLXML) value;
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (Timestamp) value;
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (Time) value;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (String) value;
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (Date) value;
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        return getNumberDefaultValue(value, BigDecimal::shortValue, () -> (short) value);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        return getNumberDefaultValue(value, BigDecimal::longValue, () -> (long) value);
//        if(value instanceof BigDecimal){
//            return ((BigDecimal)value)
//        }
//        return (long)value;
    }

    private <T> T getNumberDefaultValue(Object value, Function<BigDecimal, T> function, Supplier<T> supplier) {
        if (value instanceof BigDecimal) {
            return function.apply((BigDecimal) value);
        }
        return supplier.get();
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        return getNumberDefaultValue(value, BigDecimal::intValue, () -> (int) value);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        return getNumberDefaultValue(value, BigDecimal::floatValue, () -> (float) value);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        return getNumberDefaultValue(value, BigDecimal::doubleValue, () -> (double) value);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (Clob) value;
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        if (value == null) {
            return 0;
        }
        return (byte) value;
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (byte[]) value;
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        if (value == null) {
            return false;
        }
        return (boolean) value;
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (Blob) value;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value == null);
        return (BigDecimal) value;
    }

    @Override
    public int getReallyCount() {
        return reallyCount;
    }

    @Override
    public void close() throws Exception {
        if (closed) {
            return;
        }
        closed = true;
        memoryResultSetRowList.clear();
    }
}
