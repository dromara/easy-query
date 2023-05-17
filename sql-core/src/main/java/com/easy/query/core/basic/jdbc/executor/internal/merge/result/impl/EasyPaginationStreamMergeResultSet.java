package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.ShardingStreamResultSet;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * create time 2023/4/27 23:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyPaginationStreamMergeResultSet implements ShardingStreamResultSet {
    private final StreamMergeContext streamMergeContext;
    private final StreamResultSet streamResultSet;
    private final long offset;
    private final long rows;
    private  long realOffset;
    private  long realRows;
    private boolean closed=false;

    public EasyPaginationStreamMergeResultSet(StreamMergeContext streamMergeContext, StreamResultSet streamResultSet) throws SQLException {
        this(streamMergeContext,streamResultSet,streamMergeContext.getMergeOffset(),streamMergeContext.getMergeRows());

    }
    public EasyPaginationStreamMergeResultSet(StreamMergeContext streamMergeContext, StreamResultSet streamResultSet, long offset, long rows) throws SQLException {

        this.streamMergeContext = streamMergeContext;
        this.offset = offset;
        this.rows = rows;
        this.realOffset=0;
        this.realRows=0;
        this.streamResultSet =streamResultSet;
    }
    @Override
    public boolean hasElement() {
        return streamResultSet.hasElement();
    }

    @Override
    public boolean skipFirst() {
        return streamResultSet.skipFirst();
    }

    @Override
    public boolean next() throws SQLException {
        while(offset>realOffset){
            boolean has = streamResultSet.next();
            realOffset++;
            if(!has){
                return false;
            }
        }
        boolean next = streamResultSet.next();
        if(next){
            if(rows>0){
                realRows++;
                if(realRows>rows){
                    return false;
                }
            }
        }
        return next;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return streamResultSet.getObject(columnIndex);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return streamResultSet.wasNull();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return streamResultSet.getMetaData();
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return streamResultSet.getSQLXML(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return streamResultSet.getTimestamp(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return streamResultSet.getTime(columnIndex);
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return streamResultSet.getString(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return streamResultSet.getDate(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return streamResultSet.getShort(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return streamResultSet.getLong(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return streamResultSet.getInt(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return streamResultSet.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return streamResultSet.getDouble(columnIndex);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return streamResultSet.getClob(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return streamResultSet.getByte(columnIndex);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return streamResultSet.getBytes(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return streamResultSet.getBoolean(columnIndex);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return streamResultSet.getBlob(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return streamResultSet.getBigDecimal(columnIndex);
    }

    @Override
    public void close() throws Exception {
        if (closed) {
            return;
        }
        closed = true;
        streamResultSet.close();
    }
}
