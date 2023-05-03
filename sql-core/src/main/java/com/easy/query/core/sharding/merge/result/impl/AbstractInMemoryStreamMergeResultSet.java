package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.sharding.merge.result.InMemoryStreamMergeResultSet;

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

/**
 * create time 2023/5/3 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractInMemoryStreamMergeResultSet implements InMemoryStreamMergeResultSet {
    protected final Iterator<MemoryResultSetRow> memoryResultSetRows;
    protected final ResultSetMetaData metaData;
    protected final int columnCount;
    protected final StreamMergeContext streamMergeContext;
    protected final List<StreamResultSet> streamResultSets;
    protected final int reallyCount;
    private MemoryResultSetRow currentResultSetRow;
    private boolean wasNull;
    public AbstractInMemoryStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        this.streamMergeContext = streamMergeContext;
        this.streamResultSets=streamResultSets;
        this.metaData = streamResultSets.get(0).getMetaData();
        this.columnCount=metaData.getColumnCount();
        List<MemoryResultSetRow> memoryResultSetRows = init(streamMergeContext, streamResultSets);
        this.reallyCount=memoryResultSetRows.size();
        this.memoryResultSetRows=memoryResultSetRows.iterator();
        if(this.memoryResultSetRows.hasNext()){
            currentResultSetRow=this.memoryResultSetRows.next();
        }
    }
    protected abstract List<MemoryResultSetRow> init(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException;
    @Override
    public boolean hasElement() {
        return this.memoryResultSetRows.hasNext();
    }

    @Override
    public boolean skipFirst() {
        return false;
    }

    @Override
    public boolean next() throws SQLException {
        if(this.memoryResultSetRows.hasNext()){
            this.currentResultSetRow=this.memoryResultSetRows.next();
            return true;
        }
        return false;
    }
    private void setWasNull(boolean wasNull){
        this.wasNull=wasNull;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
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
        setWasNull(value==null);
        return (SQLXML)value;
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        return (Timestamp)value;
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        return (Time)value;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        return (String)value;
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        return (Date)value;
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        if(value==null){
            return 0;
        }
        return (short)value;
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        if(value==null){
            return 0;
        }
        return (long)value;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        if(value==null){
            return 0;
        }
        return (int)value;
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        if(value==null){
            return 0;
        }
        return (float)value;
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        if(value==null){
            return 0;
        }
        return (double)value;
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        return (Clob)value;
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        if(value==null){
            return 0;
        }
        return (byte)value;
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        return (byte[])value;
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        if(value==null){
            return false;
        }
        return (boolean)value;
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        return (Blob)value;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        Object value = currentResultSetRow.getValue(columnIndex);
        setWasNull(value==null);
        return (BigDecimal)value;
    }

    @Override
    public int getReallyCount() {
        return reallyCount;
    }

    @Override
    public void close() throws Exception {
        for (StreamResultSet streamResultSet : streamResultSets) {
            streamResultSet.close();
        }
    }
}
