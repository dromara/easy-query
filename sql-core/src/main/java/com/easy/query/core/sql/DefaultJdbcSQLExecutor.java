package com.easy.query.core.sql;

import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecuteBeforeArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EasyShardingStreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EasyStreamResultSet;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyJdbcExecutorUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create time 2025/6/7 23:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultJdbcSQLExecutor implements JdbcSQLExecutor{
    @Override
    public StreamResultSet query(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {

        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getJdbcTypeHandlerManager();
        SQLParameterPrintFormat sqlParameterPrintFormat = runtimeContext.getSQLParameterPrintFormat();
        boolean printSql = EasyJdbcExecutorUtil.isPrintSQL(executorContext);
        EasyJdbcExecutorUtil.logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
        boolean listen = jdbcExecutorListener.enable() && executorContext.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.JDBC_LISTEN);
        SQLConsumer<Statement> configurer = executorContext.getConfigurer(shardingPrint);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SQLParameter> parameters = EasyJdbcExecutorUtil.extractParameters(null, sqlParameters, printSql, sqlParameterPrintFormat, easyConnection, shardingPrint, replicaPrint);

        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
        StreamResultSet sr = null;
        Exception exception = null;
        try {
            if (listen) {
                String traceId = jdbcExecutorListener.createTraceId();
                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, Collections.singletonList(parameters), executorContext.getExecuteMethod());
                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
            }
            ps = EasyJdbcExecutorUtil.createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler);

            long start = printSql ? System.currentTimeMillis() : 0L;
            if (configurer != null) {
                configurer.accept(ps);
            }
            rs = ps.executeQuery();
            long end = printSql ? System.currentTimeMillis() : 0L;
            if (printSql) {
                EasyJdbcExecutorUtil.logUse(true, start, end, easyConnection, shardingPrint, replicaPrint);
            }
            //如果是分片查询那么需要提前next
            if (shardingPrint) {
                boolean next = rs.next();
                sr = new EasyShardingStreamResultSet(rs, ps, next);
            } else {
                sr = new EasyStreamResultSet(rs, ps);
            }

        } catch (Exception e) {
            exception = e;
//            log.error(sql, e);
            if (e instanceof SQLException) {
                throw new EasyQuerySQLStatementException(sql, e);
            } else {
                throw e;
            }
        } finally {
            if (listen) {
                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, sr, 0, exception));
            }
        }
        return sr;
    }

    @Override
    public <T> int insert(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean fillAutoIncrement, boolean shardingPrint, boolean replicaPrint) throws SQLException {

        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
        boolean listen = jdbcExecutorListener.enable() && executorContext.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.JDBC_LISTEN);

        boolean printSql = EasyJdbcExecutorUtil.isPrintSQL(executorContext);
        EasyJdbcExecutorUtil.logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);

        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getJdbcTypeHandlerManager();
        SQLParameterPrintFormat sqlParameterPrintFormat = runtimeContext.getSQLParameterPrintFormat();
        Class<?> entityClass = entities.get(0).getClass();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
        List<String> generatedKeyColumns = fillAutoIncrement ? entityMetadata.getGeneratedKeyColumns() : null;
        if (fillAutoIncrement && EasyCollectionUtil.isEmpty(generatedKeyColumns)) {
            throw new EasyQueryInvalidOperationException("Database return key not found. Please ensure the object:[" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + "] has a property configured with the [@Column(generatedKey = true)] for the required back fill.");
        }
        PreparedStatement ps = null;
        Exception exception = null;
        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
        int r = 0;
        try {
            if (listen) {
                String traceId = jdbcExecutorListener.createTraceId();
                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, new ArrayList<>(entities.size()), executorContext.getExecuteMethod());
                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
            }
            int batchSize = 0;
            for (T entity : entities) {
                batchSize++;
                List<SQLParameter> parameters = EasyJdbcExecutorUtil.extractParameters(entity, sqlParameters, printSql, sqlParameterPrintFormat, easyConnection, shardingPrint, replicaPrint);
                if (listen) {
                    jdbcListenBeforeArg.getSqlParameters().add(parameters);
                }
                if (ps == null) {
                    ps = EasyJdbcExecutorUtil.createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler, generatedKeyColumns);
                } else {
                    EasyJdbcExecutorUtil.setPreparedStatement(ps, parameters, easyJdbcTypeHandler);
                }
                r += EasyJdbcExecutorUtil.execute(entities.size() > 1, batchSize, ps, (params) -> {
                    EasyJdbcExecutorUtil.incrementBackFill(fillAutoIncrement, params.getAlreadyCommitSize(), generatedKeyColumns, params.getPs(), entities, entityMetadata);
                });
            }
            r += EasyJdbcExecutorUtil.executeEnd(entities.size() > 1, batchSize, ps, (params) -> {
                EasyJdbcExecutorUtil.incrementBackFill(fillAutoIncrement, params.getAlreadyCommitSize(), generatedKeyColumns, params.getPs(), entities, entityMetadata);
            });
            EasyJdbcExecutorUtil.logResult(printSql, r, easyConnection, shardingPrint, replicaPrint);
        } catch (Exception e) {
            exception = e;
//            log.error(sql, e);
            if (e instanceof SQLException) {
                throw new EasyQuerySQLStatementException(sql, e);
            } else {
                throw e;
            }
        } finally {
            EasyJdbcExecutorUtil.clear(ps);
            if (listen) {
                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, null, r, exception));
            }
        }
        return r;
    }

    @Override
    public <T> int executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {
        boolean printSql = EasyJdbcExecutorUtil.isPrintSQL(executorContext);
        EasyJdbcExecutorUtil.logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
        boolean listen = jdbcExecutorListener.enable() && executorContext.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.JDBC_LISTEN);
        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getJdbcTypeHandlerManager();
        SQLParameterPrintFormat sqlParameterPrintFormat = runtimeContext.getSQLParameterPrintFormat();
        PreparedStatement ps = null;
        Exception exception = null;
        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
        int r = 0;
        try {
            if (listen) {
                String traceId = jdbcExecutorListener.createTraceId();
                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, new ArrayList<>(entities.size()), executorContext.getExecuteMethod());
                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
            }
            int batchSize = 0;
            for (T entity : entities) {
                batchSize++;
                List<SQLParameter> parameters = EasyJdbcExecutorUtil.extractParameters(entity, sqlParameters, printSql, sqlParameterPrintFormat, easyConnection, shardingPrint, replicaPrint);
                if (listen) {
                    jdbcListenBeforeArg.getSqlParameters().add(parameters);
                }
                if (ps == null) {
                    ps = EasyJdbcExecutorUtil.createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
                } else {
                    EasyJdbcExecutorUtil.setPreparedStatement(ps, parameters, easyJdbcTypeHandlerManager);
                }
                r += EasyJdbcExecutorUtil.execute(entities.size() > 1, batchSize, ps, null);
            }
            r += EasyJdbcExecutorUtil.executeEnd(entities.size() > 1, batchSize, ps, null);
            EasyJdbcExecutorUtil.logResult(printSql, r, easyConnection, shardingPrint, replicaPrint);
        } catch (Exception e) {
            exception = e;
//            log.error(sql, e);
            if (e instanceof SQLException) {
                throw new EasyQuerySQLStatementException(sql, e);
            } else {
                throw e;
            }
        } finally {
            EasyJdbcExecutorUtil.clear(ps);
            if (listen) {
                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, null, r, exception));
            }

            if (EasyCollectionUtil.isNotEmpty(entities)) {
                Class<?> entityClass = entities.get(0).getClass();

                boolean trackBean = EasyTrackUtil.trackBean(executorContext, entityClass);
                if (trackBean) {

                    TrackManager trackManager = executorContext.getRuntimeContext().getTrackManager();
                    boolean hasTracked = trackManager.getCurrentTrackContext().hasTracked(entityClass);
                    if (hasTracked) {
                        for (T entity : entities) {
                            trackManager.getCurrentTrackContext().removeTracking(entity);
                        }
                    }
                }
            }
        }
        return r;
    }

    @Override
    public int executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {
        boolean printSql = EasyJdbcExecutorUtil.isPrintSQL(executorContext);
        EasyJdbcExecutorUtil.logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getJdbcTypeHandlerManager();
        SQLParameterPrintFormat sqlParameterPrintFormat = runtimeContext.getSQLParameterPrintFormat();

        List<SQLParameter> parameters = EasyJdbcExecutorUtil.extractParameters(null, sqlParameters, printSql, sqlParameterPrintFormat, easyConnection, shardingPrint, replicaPrint);

        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
        boolean listen = jdbcExecutorListener.enable() && executorContext.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.JDBC_LISTEN);
        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
        PreparedStatement ps = null;
        Exception exception = null;
        int r = 0;
        try {
            if (listen) {
                String traceId = jdbcExecutorListener.createTraceId();
                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, Collections.singletonList(parameters), executorContext.getExecuteMethod());
                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
            }
            ps = EasyJdbcExecutorUtil.createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
            r = ps.executeUpdate();
            EasyJdbcExecutorUtil.logResult(printSql, r, easyConnection, shardingPrint, replicaPrint);
        } catch (Exception e) {
            exception = e;
//            log.error(sql, e);
            if (e instanceof SQLException) {
                throw new EasyQuerySQLStatementException(sql, e);
            } else {
                throw e;
            }
        } finally {
            EasyJdbcExecutorUtil.clear(ps);
            if (listen) {
                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, null, r, exception));
            }
        }
        return r;
    }
}
