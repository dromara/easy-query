package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.single;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row.ConstMemoryResultSetRow;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.JdbcShardingStreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.AbstractInMemoryStreamMergeResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row.MemoryResultSetRow;

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
        ShardingQueryCountManager shardingQueryCountManager = streamMergeContext.getRuntimeContext().getShardingQueryCountManager();
        boolean recordCount = streamMergeContext.hasBehavior(MergeBehaviorEnum.SEQUENCE_COUNT);//顺序查询才会记录
        long result=0;
        for (StreamResultSet resultSet : streamResultSets) {
            try (StreamResultSet streamResultSet = resultSet) {
                while (streamResultSet.next()) {
                    long rows = streamResultSet.getLong(1);
                    result=result+rows;
                    if(recordCount){
                        if(streamResultSet instanceof JdbcShardingStreamResultSet){
                            shardingQueryCountManager.addCountResult(rows,false);
                        }
                    }
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
