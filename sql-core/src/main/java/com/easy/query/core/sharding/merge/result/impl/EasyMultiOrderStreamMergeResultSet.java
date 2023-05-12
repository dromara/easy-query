package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.sharding.merge.result.OrderStreamMergeResult;
import com.easy.query.core.util.EasyCollectionUtil;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * create time 2023/4/20 22:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyMultiOrderStreamMergeResultSet implements StreamResultSet {

    private final StreamMergeContext streamMergeContext;
    private final List<StreamResultSet> orderStreamMergeResults;
    private final Queue<OrderStreamMergeResult> queue;
    private StreamResultSet currentStreamResult;
    private boolean skipFirst;

    private boolean closed=false;

    public EasyMultiOrderStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResults) throws SQLException {

        this.streamMergeContext = streamMergeContext;
        this.orderStreamMergeResults = streamResults;
        this.queue =new PriorityQueue<>(streamResults.size());
        skipFirst=true;
        setOrderStreamResult();
    }

    private void setOrderStreamResult() throws SQLException {
        for (StreamResultSet orderStreamMergeResult : this.orderStreamMergeResults) {
            EasyOrderStreamMergeResultSet easyOrderStreamMergeResult = new EasyOrderStreamMergeResultSet(streamMergeContext, orderStreamMergeResult);
            if(easyOrderStreamMergeResult.hasElement()){
                easyOrderStreamMergeResult.skipFirst();
                queue.offer(easyOrderStreamMergeResult);
            }
        }
        currentStreamResult= queue.isEmpty()? EasyCollectionUtil.firstOrNull(orderStreamMergeResults) : queue.peek();
    }

    @Override
    public boolean hasElement() {
        return currentStreamResult!=null&&currentStreamResult.hasElement();
    }

    @Override
    public boolean skipFirst() {
        if (skipFirst)
        {
            skipFirst = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean next() throws SQLException {
        if(queue.isEmpty()){
            return false;
        }
        if(skipFirst){
            skipFirst=false;
            return true;
        }
        OrderStreamMergeResult first = queue.poll();
        if(first.next()){
            queue.offer(first);
        }
        if(queue.isEmpty()){
            return false;
        }
        currentStreamResult=queue.peek();
        return true;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return currentStreamResult.getObject(columnIndex);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return currentStreamResult.wasNull();
    }
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return currentStreamResult.getMetaData();
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return currentStreamResult.getSQLXML(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return currentStreamResult.getTimestamp(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return currentStreamResult.getTime(columnIndex);
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return currentStreamResult.getString(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return currentStreamResult.getDate(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return currentStreamResult.getShort(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return currentStreamResult.getLong(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return currentStreamResult.getInt(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return currentStreamResult.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return currentStreamResult.getDouble(columnIndex);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return currentStreamResult.getClob(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return currentStreamResult.getByte(columnIndex);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return currentStreamResult.getBytes(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return currentStreamResult.getBoolean(columnIndex);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return currentStreamResult.getBlob(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return currentStreamResult.getBigDecimal(columnIndex);
    }

    @Override
    public void close() throws Exception {
        if (closed) {
            return;
        }
        closed = true;
        for (StreamResultSet streamResult : orderStreamMergeResults) {
            streamResult.close();
        }
    }
}
