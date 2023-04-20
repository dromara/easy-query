package com.easy.query.core.sharding.merge.executor.internal;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.executor.common.CommandExecuteUnit;
import com.easy.query.core.sharding.merge.impl.DefaultStreamResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * create time 2023/4/14 11:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyStreamMergeExecutor extends AbstractExecutor<ExecuteResult> {

    public EasyStreamMergeExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected ExecuteResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit) {
        PreparedStatement preparedStatement = commandExecuteUnit.getPreparedStatement();
        CommandTypeEnum commandType = commandExecuteUnit.getCommandType();
        switch (commandType) {
            case QUERY:
                return executeQuery(preparedStatement);
            case EXECUTE_BATCH:
                return executeBatch(preparedStatement);
            case EXECUTE:
                return execute(preparedStatement);
        }
        throw new EasyQueryException("not implement:" + commandType);
    }

    private ExecuteResult executeQuery(PreparedStatement preparedStatement) {
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
        return new QueryExecuteResult(new DefaultStreamResult(resultSet,preparedStatement), preparedStatement);
    }

    private ExecuteResult executeBatch(PreparedStatement preparedStatement) {
        int rows = 0;
        try {
            rows = preparedStatement.executeBatch().length;
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
        return new AffectedRowsExecuteResult(rows, preparedStatement);
    }

    private ExecuteResult execute(PreparedStatement preparedStatement) {
        int rows = 0;
        try {
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
        return new AffectedRowsExecuteResult(rows, preparedStatement);
    }

    @Override
    public ShardingMerger<ExecuteResult> getShardingMerger() {
        return null;
    }
}
