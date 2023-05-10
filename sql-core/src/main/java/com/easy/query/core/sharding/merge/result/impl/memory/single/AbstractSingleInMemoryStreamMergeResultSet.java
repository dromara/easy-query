package com.easy.query.core.sharding.merge.result.impl.memory.single;

import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.sharding.merge.result.impl.memory.AbstractInMemoryStreamMergeResultSet;
import com.easy.query.core.sharding.merge.result.impl.memory.row.ConstMemoryResultSetRow;
import com.easy.query.core.sharding.merge.result.impl.memory.row.MemoryResultSetRow;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/5/10 11:12
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSingleInMemoryStreamMergeResultSet extends AbstractInMemoryStreamMergeResultSet {
    public AbstractSingleInMemoryStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        super(streamMergeContext, streamResultSets);
    }

    @Override
    protected List<MemoryResultSetRow> init(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        int columnCount = metaData.getColumnCount();
        if(columnCount!=1){
            throw new SQLException("column count "+columnCount+" !=1");
        }
        Object resultValue = resultValue();
        MemoryResultSetRow resultRow=null;
        for (StreamResultSet resultSet : streamResultSets) {
            try (StreamResultSet streamResultSet = resultSet) {
                //调用为了streamResultSet可以被close
                if(resultRow!=null){
                    continue;
                }
                while (streamResultSet.next()) {
                    boolean isResult = Objects.equals(streamResultSet.getLong(1), resultValue);
                    if(isResult){
                        resultRow=new ConstMemoryResultSetRow(resultValue);
                        break;
                    }
                }
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
        ArrayList<MemoryResultSetRow> list = new ArrayList<>(1);
        if(resultRow!=null){
            list.add(resultRow);
        } else{
            Object defaultValue = defaultValue();
            list.add(new ConstMemoryResultSetRow(defaultValue));
        }
        return list;
    }

    /**
     * 遇到这个值就返回
     * @return
     */
    protected abstract Object resultValue();

    /**
     * 遇到这个值不返回
     * @return
     */
    protected abstract Object defaultValue();
}
