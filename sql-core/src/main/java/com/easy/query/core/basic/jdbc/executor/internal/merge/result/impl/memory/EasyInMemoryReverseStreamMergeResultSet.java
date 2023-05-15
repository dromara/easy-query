package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row.DefaultMemoryResultSetRow;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row.MemoryResultSetRow;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;
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
