package com.easy.query.core.sharding.merge;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/13 11:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StreamMergeContext extends AutoCloseable {

 ExecutorContext getExecutorContext();
   Collection<ExecutionUnit> getExecutionUnits();


    EasyQueryRuntimeContext getRuntimeContext();

    boolean isSerialExecute();

    boolean isSeqQuery();

    List<EasyConnection> getEasyConnections(ConnectionModeEnum connectionMode, String dataSourceName, int createDbConnectionCount);
}
