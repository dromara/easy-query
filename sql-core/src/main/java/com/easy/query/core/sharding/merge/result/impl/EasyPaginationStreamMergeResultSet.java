package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.StreamResultSet;

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
public class EasyPaginationStreamMergeResultSet implements StreamResultSet {
    private final StreamMergeContext streamMergeContext;
    private final StreamResultSet streamResult;
    private final long offset;
    private final long rows;
    private  long realOffset;
    private  long realRows;
    private boolean closed=false;

    public EasyPaginationStreamMergeResultSet(StreamMergeContext streamMergeContext, StreamResultSet streamResult) throws SQLException {
        this(streamMergeContext,streamResult,streamMergeContext.getOffset(),streamMergeContext.getRows());

    }
    public EasyPaginationStreamMergeResultSet(StreamMergeContext streamMergeContext, StreamResultSet streamResult, long offset, long rows) throws SQLException {

        this.streamMergeContext = streamMergeContext;
        this.offset = offset;
        this.rows = rows;
        this.realOffset=0;
        this.realRows=0;
        this.streamResult=streamResult;
    }
    @Override
    public boolean hasElement() {
        return streamResult.hasElement();
    }

    @Override
    public boolean skipFirst() {
        return streamResult.skipFirst();
    }

    @Override
    public boolean next() throws SQLException {
        while(offset>realOffset){
            boolean has = streamResult.next();
            realOffset++;
            if(!has){
                return false;
            }
        }
        boolean next = streamResult.next();
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
        return streamResult.getObject(columnIndex);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return streamResult.wasNull();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return streamResult.getMetaData();
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return streamResult.getSQLXML(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return streamResult.getTimestamp(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return streamResult.getTime(columnIndex);
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return streamResult.getString(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return streamResult.getDate(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return streamResult.getShort(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return streamResult.getLong(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return streamResult.getInt(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return streamResult.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return streamResult.getDouble(columnIndex);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return streamResult.getClob(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return streamResult.getByte(columnIndex);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return streamResult.getBytes(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return streamResult.getBoolean(columnIndex);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return streamResult.getBlob(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return streamResult.getBigDecimal(columnIndex);
    }

    @Override
    public void close() throws Exception {
        if (closed) {
            return;
        }
        closed = true;
        streamResult.close();
    }
}
