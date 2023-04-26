package com.easy.query.core.basic.jdbc.executor.internal;

import java.sql.PreparedStatement;

/**
 * create time 2023/4/14 16:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class AffectedRowsExecuteResult implements ExecuteResult{
    private static final AffectedRowsExecuteResult instance=new AffectedRowsExecuteResult(0);
    public static AffectedRowsExecuteResult empty(){
        return instance;
    }
    private final long rows;

    public AffectedRowsExecuteResult(long rows){

        this.rows = rows;
    }

    public long getRows() {
        return rows;
    }


    @Override
    public void close() throws Exception {
    }
}
