package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/5/2 23:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyInMemoryStreamMergeResultSet extends AbstractInMemoryStreamMergeResultSet {
    public EasyInMemoryStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        super(streamMergeContext, streamResultSets);
    }

    @Override
    protected List<MemoryResultSetRow> init(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        ArrayList<MemoryResultSetRow> list = new ArrayList<>(1024);
        int columnCount = metaData.getColumnCount();
        for (StreamResultSet resultSet : streamResultSets) {
            try (StreamResultSet streamResultSet = resultSet) {
                while (streamResultSet.next()) {
                    list.add(new MemoryResultSetRow(streamResultSet, columnCount));
                }
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
        return list;
    }
}
