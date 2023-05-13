package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.result.ExecutionUnitStreamResult;
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
 * create time 2023/5/13 00:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyExecutionUnitStreamResult implements ExecutionUnitStreamResult {
    private final StreamResultSet streamResultSet;
    private final ExecutionUnit executionUnit;

    public EasyExecutionUnitStreamResult(StreamResultSet streamResultSet, ExecutionUnit executionUnit){

        this.streamResultSet = streamResultSet;
        this.executionUnit = executionUnit;
    }
    @Override
    public ExecutionUnit getExecutionUnit() {
        return executionUnit;
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
        return streamResultSet.next();
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
        streamResultSet.close();
    }
}
