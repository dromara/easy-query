package com.easy.query.core.sharding.merge.executor.internal;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.executor.common.CommandExecuteUnit;
import com.easy.query.core.sharding.merge.executor.merger.DefaultStreamShardingMerger0;
import com.easy.query.core.sharding.merge.impl.DefaultStreamResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * create time 2023/4/16 22:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultStreamExecutor extends AbstractExecutor<StreamResult> {
    public DefaultStreamExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected StreamResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit) {
        PreparedStatement preparedStatement = commandExecuteUnit.getPreparedStatement();
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
       return  new DefaultStreamResult(resultSet);
    }

    @Override
    public ShardingMerger<StreamResult> getShardingMerger() {
        return DefaultStreamShardingMerger0.getInstance();
    }
}
