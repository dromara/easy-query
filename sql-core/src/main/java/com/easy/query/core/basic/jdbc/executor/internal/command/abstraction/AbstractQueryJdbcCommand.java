package com.easy.query.core.basic.jdbc.executor.internal.command.abstraction;

import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.result.impl.DefaultCommandQueryExecuteResult;
import com.easy.query.core.exception.EasyQueryRouteNotMatchException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.Collection;

/**
 * create time 2023/5/11 13:32
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractQueryJdbcCommand extends AbstractJdbcCommand<QueryExecuteResult>{
    private static final Log log= LogFactory.getLog(AbstractQueryJdbcCommand.class);
    public AbstractQueryJdbcCommand(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    public QueryExecuteResult execute() throws SQLException {
        if(streamMergeContext.isSharding()){
            Collection<ExecutionUnit> executionUnits = streamMergeContext.getExecutionUnits();
            if(EasyCollectionUtil.isEmpty(executionUnits)){
                if(streamMergeContext.getEasyQueryOption().isThrowIfRouteNotMatch()){
                    throw new EasyQueryRouteNotMatchException("sharding query route not match");
                }
                if(!streamMergeContext.isQuery()){
                    throw new EasyQueryRouteNotMatchException("sharding execute route not match");
                }
                log.debug("sharding route empty result default");
                return defaultResult();
            }
        }
        return super.execute();
    }

    @Override
    protected QueryExecuteResult defaultResult() {
        return DefaultCommandQueryExecuteResult.EMPTY;
    }
}
