package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.result.OrderStreamMergeResult;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * create time 2023/4/27 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyMultiAggregateOrderStreamMergeResult implements StreamResult {
    private final StreamMergeContext streamMergeContext;
    private final List<StreamResult> streamResults;
    private final Queue<OrderStreamMergeResult> queue;
    private List<Object> currentGroupValues;
    private boolean skipFirst;

    public EasyMultiAggregateOrderStreamMergeResult(StreamMergeContext streamMergeContext, List<StreamResult> streamResults){

        this.streamMergeContext = streamMergeContext;
        this.streamResults = streamResults;
        this.queue =new PriorityQueue<>(streamResults.size());
        this.skipFirst=true;
    }

    private void setOrderStreamResult(){
        for (StreamResult streamResult : streamResults) {
            EasyOrderStreamMergeResult easyOrderStreamMergeResult = new EasyOrderStreamMergeResult(streamMergeContext, streamResult);
            if(easyOrderStreamMergeResult.hasElement()){
                easyOrderStreamMergeResult.skipFirst();
                queue.offer(easyOrderStreamMergeResult);
            }
        }
        currentGroupValues=queue.isEmpty()? Collections.emptyList():getCurrentGroupValues(queue.peek());
    }

    //todo
    private List<Object> getCurrentGroupValues(OrderStreamMergeResult orderStreamMergeResult){
        return Collections.emptyList();
    }
    @Override
    public boolean hasElement() {
        return false;
    }

    @Override
    public boolean skipFirst() {
        return false;
    }

    @Override
    public boolean next() throws SQLException {
        return false;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public boolean wasNull() throws SQLException {
        return false;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return new byte[0];
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return false;
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
