package com.easy.query.core.sharding.merge.executor.internal;

import java.sql.PreparedStatement;

/**
 * create time 2023/4/14 16:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class AffectedRowsExecuteResult implements ExecuteResult{
    private final int rows;
    private final PreparedStatement preparedStatement;

    public AffectedRowsExecuteResult(int rows, PreparedStatement preparedStatement){

        this.rows = rows;
        this.preparedStatement = preparedStatement;
    }

    public int getRows() {
        return rows;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    @Override
    public void close() throws Exception {
        if(preparedStatement!=null){
            preparedStatement.close();
        }
    }
}
