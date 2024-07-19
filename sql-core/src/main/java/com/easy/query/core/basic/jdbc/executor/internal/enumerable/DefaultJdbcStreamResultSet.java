package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.command.JdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * create time 2023/7/31 16:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultJdbcStreamResultSet<T> implements JdbcStreamResult<T> {
    private final ExecutorContext executorContext;
    private final ResultMetadata<T> resultMetadata;
    private final JdbcCommand<QueryExecuteResult> command;
    private QueryExecuteResult queryExecuteResult;

    public DefaultJdbcStreamResultSet(ExecutorContext executorContext, ResultMetadata<T> resultMetadata, JdbcCommand<QueryExecuteResult> command){
        this.executorContext = executorContext;
        this.resultMetadata = resultMetadata;
        this.command = command;
    }
    @Override
    public StreamIterable<T> getStreamIterable() throws SQLException {
        if(queryExecuteResult==null){
            this.queryExecuteResult=command.execute();
        }
        return new DefaultStreamIterable<>(executorContext,resultMetadata,this.queryExecuteResult.getStreamResultSet());
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public void toChunk(int size, Predicate<List<T>> chunk) throws SQLException {
        List<T> entities = new ArrayList<>(size);
        for (T result : this.getStreamIterable()) {
            entities.add(result);
            if (entities.size() == size) {
                boolean c = chunk.test(entities);
                entities.clear();
                if (!c) {
                    break;
                }
            }
        }
        if (EasyCollectionUtil.isNotEmpty(entities)) {
            chunk.test(entities);
            entities.clear();
        }
    }

    @Override
    public void close() throws SQLException {
        command.close();
        if(this.queryExecuteResult!=null){
            this.queryExecuteResult.close();
        }
    }
}
