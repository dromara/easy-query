package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyJdbcExecutorUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/7/31 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultJdbcResult<TR> implements JdbcResult<TR> {
    private static final Log log = LogFactory.getLog(DefaultJdbcResult.class);
    private final JdbcStreamResult<TR> jdbcStreamResult;

    public DefaultJdbcResult(JdbcStreamResult<TR> jdbcStreamResult) {

        this.jdbcStreamResult = jdbcStreamResult;
    }

    @Override
    public JdbcStreamResult<TR> getJdbcStreamResult() {
        return jdbcStreamResult;
    }

    @Override
    public List<TR> toList() {
        try (JdbcStreamResult<TR> jdbcStreamResultSet = jdbcStreamResult) {
            StreamIterable<TR> streamResult = jdbcStreamResultSet.getStreamIterable();
            List<TR> list = EasyCollectionUtil.newArrayList(streamResult);
            if (EasyJdbcExecutorUtil.isPrintSQL(jdbcStreamResult.getExecutorContext())) {

                log.info("<== Total: " + list.size());
                JdbcExecutorListener jdbcExecutorListener = jdbcStreamResult.getExecutorContext().getRuntimeContext().getJdbcExecutorListener();
                if (jdbcExecutorListener.enable()) {
                    jdbcExecutorListener.onQueryRows(jdbcStreamResult.getExecutorContext().getJdbcListenerTraceId(), list.size());
                }
            }
            return list;
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }
}
