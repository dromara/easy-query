package com.easy.query.core.sharding.merge.result.impl.memory.single;

import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.sharding.merge.result.impl.memory.AbstractInMemoryStreamMergeResultSet;
import com.easy.query.core.sharding.merge.result.impl.memory.row.ConstMemoryResultSetRow;
import com.easy.query.core.sharding.merge.result.impl.memory.row.MemoryResultSetRow;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/5/10 11:12
 * 文件说明
 *
 * @author xuejiaming
 */
public  class EasyCountInMemoryStreamMergeResultSet extends AbstractInMemoryStreamMergeResultSet {
    public EasyCountInMemoryStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        super(streamMergeContext, streamResultSets);
    }

    @Override
    protected List<MemoryResultSetRow> init(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        int columnCount = metaData.getColumnCount();
        if(columnCount!=1){
            throw new SQLException("column count "+columnCount+" !=1");
        }
        long result=0;
        for (StreamResultSet resultSet : streamResultSets) {
            try (StreamResultSet streamResultSet = resultSet) {
                while (streamResultSet.next()) {
                    long rows = streamResultSet.getLong(1);
                    result=result+rows;
                }
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
        ArrayList<MemoryResultSetRow> list = new ArrayList<>(1);
        list.add(new ConstMemoryResultSetRow(result));
        return list;
    }
}
