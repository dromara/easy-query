package com.easy.query.core.sharding.merge.result.impl.memory;

import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.InMemoryStreamMergeResultSet;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.sharding.merge.result.impl.memory.row.DefaultMemoryResultSetRow;
import com.easy.query.core.sharding.merge.result.impl.memory.row.MemoryResultSetRow;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * create time 2023/5/2 23:48
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyInMemoryReverseStreamMergeResultSet extends AbstractInMemoryStreamMergeResultSet {
    public EasyInMemoryReverseStreamMergeResultSet(StreamMergeContext streamMergeContext, StreamResultSet streamResultSet) throws SQLException {
        super(streamMergeContext, Collections.singletonList(streamResultSet));
    }

    @Override
    protected List<MemoryResultSetRow> init(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        LinkedList<MemoryResultSetRow> list = new LinkedList<>();
        int columnCount = metaData.getColumnCount();
        for (StreamResultSet resultSet : streamResultSets) {
            try (StreamResultSet streamResultSet = resultSet) {
                while (streamResultSet.next()) {
                    list.addFirst(new DefaultMemoryResultSetRow(streamResultSet, columnCount));
                }
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
        return list;
    }
}
