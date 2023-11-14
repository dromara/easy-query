//package com.easy.query.core.basic.jdbc.executor.sql;
//
//import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
//import com.easy.query.core.basic.extension.listener.JdbcExecuteBeforeArg;
//import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
//import com.easy.query.core.basic.extension.track.TrackManager;
//import com.easy.query.core.basic.jdbc.conn.EasyConnection;
//import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
//import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
//import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EasyShardingStreamResultSet;
//import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EasyStreamResultSet;
//import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
//import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
//import com.easy.query.core.context.QueryRuntimeContext;
//import com.easy.query.core.exception.EasyQuerySQLStatementException;
//import com.easy.query.core.expression.lambda.PropertySetterCaller;
//import com.easy.query.core.metadata.ColumnMetadata;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.util.EasyClassUtil;
//import com.easy.query.core.util.EasyCollectionUtil;
//import com.easy.query.core.util.EasyTrackUtil;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * create time 2023/11/14 17:14
// * 文件说明
// *
// * @author xuejiaming
// */
//public class DefaultJdbcSQLExecutor extends AbstractBaseJdbcSQLExecutor {
//    @Override
//    public StreamResultSet query(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {
//        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
//        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
//        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getJdbcTypeHandlerManager();
//        boolean printSql = executorContext.getEasyQueryOption().isPrintSql();
//        logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
//        boolean listen = jdbcExecutorListener.enable();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<SQLParameter> parameters = extractParameters(null, sqlParameters, printSql, easyConnection, shardingPrint, replicaPrint);
//
//        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
//        StreamResultSet sr = null;
//        Exception exception = null;
//        try {
//            if (listen) {
//                String traceId = jdbcExecutorListener.createTraceId();
//                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, Collections.singletonList(sqlParameters));
//                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
//            }
//            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler);
//
//            long start = printSql ? System.currentTimeMillis() : 0L;
//            rs = ps.executeQuery();
//            long end = printSql ? System.currentTimeMillis() : 0L;
//            if (printSql) {
//                logUse(true, start, end, easyConnection, shardingPrint, replicaPrint);
//            }
//            //如果是分片查询那么需要提前next
//            if (shardingPrint) {
//                boolean next = rs.next();
//                sr = new EasyShardingStreamResultSet(rs, ps, next);
//            } else {
//                sr = new EasyStreamResultSet(rs, ps);
//            }
//
//        } catch (Exception e) {
//            exception = e;
//            log.error(sql, e);
//            if (e instanceof SQLException) {
//                throw new EasyQuerySQLStatementException(sql, e);
//            } else {
//                throw e;
//            }
//        } finally {
//            if (listen) {
//                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, 0, exception));
//            }
//        }
//        return sr;
//    }
//
//    @Override
//    public <T> int insert(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean fillAutoIncrement, boolean shardingPrint, boolean replicaPrint) throws SQLException {
//
//        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
//        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
//        boolean listen = jdbcExecutorListener.enable();
//        boolean printSql = executorContext.getEasyQueryOption().isPrintSql();
//        logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
//
//        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getJdbcTypeHandlerManager();
//        Class<?> entityClass = entities.get(0).getClass();
//        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
//        List<String> generatedKeyColumns = fillAutoIncrement ? entityMetadata.getGeneratedKeyColumns() : null;
//        PreparedStatement ps = null;
//        Exception exception = null;
//        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
//        int r = 0;
//        try {
//            if (listen) {
//                String traceId = jdbcExecutorListener.createTraceId();
//                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, new ArrayList<>(entities.size()));
//                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
//            }
//            int batchSize = 0;
//            for (T entity : entities) {
//                batchSize++;
//                List<SQLParameter> parameters = extractParameters(entity, sqlParameters, printSql, easyConnection, shardingPrint, replicaPrint);
//                if (listen) {
//                    jdbcListenBeforeArg.getSqlParameters().add(parameters);
//                }
//                if (ps == null) {
//                    ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler, generatedKeyColumns);
//                } else {
//                    setPreparedStatement(ps, parameters, easyJdbcTypeHandler);
//                }
//                ps.addBatch();
//                if ((batchSize % BATCH_GROUP_COUNT) == 0) {
//                    int[] ints = ps.executeBatch();
////                    r += ints.length;
//                    r += EasyCollectionUtil.sum(ints);
//                    ps.clearBatch();
//                }
//            }
//            if ((batchSize % BATCH_GROUP_COUNT) != 0) {
//                int[] ints = ps.executeBatch();
////                r += ints.length;
//                r += EasyCollectionUtil.sum(ints);
//                ps.clearBatch();
//            }
//            //如果需要自动填充并且存在自动填充列
//            if (fillAutoIncrement && EasyCollectionUtil.isNotEmpty(generatedKeyColumns)) {
//                assert ps != null;
//                ResultSet keysSet = ps.getGeneratedKeys();
//                int index = 0;
//                ColumnMetadata[] columnMetadatas = new ColumnMetadata[generatedKeyColumns.size()];
//                while (keysSet.next()) {
//                    T entity = entities.get(index);
//                    for (int i = 0; i < generatedKeyColumns.size(); i++) {
//                        ColumnMetadata columnMetadata = columnMetadatas[i];
//                        if (columnMetadata == null) {
//                            String columnName = generatedKeyColumns.get(i);
//                            String propertyName = entityMetadata.getPropertyNameNotNull(columnName);
//                            columnMetadata = entityMetadata.getColumnNotNull(propertyName);
//                            columnMetadatas[i] = columnMetadata;
//                        }
//
//                        Object value = keysSet.getObject(i + 1);
//                        Object newValue = EasyClassUtil.convertValueToRequiredType(value, columnMetadata.getPropertyType());
//                        PropertySetterCaller<Object> beanSetter = columnMetadata.getSetterCaller();
//                        beanSetter.call(entity, newValue);
////                        Method setter = getSetter(property, entityClass);
////                        callSetter(entity,setter, property, newValue);
//                    }
//                    index++;
//                }
//            }
//            logResult(printSql, r, easyConnection, shardingPrint, replicaPrint);
//        } catch (Exception e) {
//            exception = e;
//            log.error(sql, e);
//            if (e instanceof SQLException) {
//                throw new EasyQuerySQLStatementException(sql, e);
//            } else {
//                throw e;
//            }
//        } finally {
//            clear(ps);
//            if (listen) {
//                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, r, exception));
//            }
//        }
//        return r;
//    }
//
//    @Override
//    public <T> int executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {
//        boolean printSql = executorContext.getEasyQueryOption().isPrintSql();
//        logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
//        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
//        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
//        boolean listen = jdbcExecutorListener.enable();
//        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getJdbcTypeHandlerManager();
//        PreparedStatement ps = null;
//        Exception exception = null;
//        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
//        int r = 0;
//        try {
//            if (listen) {
//                String traceId = jdbcExecutorListener.createTraceId();
//                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, new ArrayList<>(entities.size()));
//                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
//            }
//            int batchSize = 0;
//            for (T entity : entities) {
//                batchSize++;
//                List<SQLParameter> parameters = extractParameters(entity, sqlParameters, printSql, easyConnection, shardingPrint, replicaPrint);
//                if (listen) {
//                    jdbcListenBeforeArg.getSqlParameters().add(parameters);
//                }
//                if (ps == null) {
//                    ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
//                } else {
//                    setPreparedStatement(ps, parameters, easyJdbcTypeHandlerManager);
//                }
//                ps.addBatch();
//                if ((batchSize % BATCH_GROUP_COUNT) == 0) {
//                    int[] ints = ps.executeBatch();
//                    r += EasyCollectionUtil.sum(ints);
//                    ps.clearBatch();
//                }
//            }
//            if ((batchSize % BATCH_GROUP_COUNT) != 0) {
//                int[] ints = ps.executeBatch();
//                r += EasyCollectionUtil.sum(ints);
//                ps.clearBatch();
//            }
//            logResult(printSql, r, easyConnection, shardingPrint, replicaPrint);
//        } catch (Exception e) {
//            exception = e;
//            log.error(sql, e);
//            if (e instanceof SQLException) {
//                throw new EasyQuerySQLStatementException(sql, e);
//            } else {
//                throw e;
//            }
//        } finally {
//            clear(ps);
//            if (listen) {
//                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, r, exception));
//            }
//
//            if (EasyCollectionUtil.isNotEmpty(entities)) {
//                Class<?> entityClass = entities.get(0).getClass();
//
//                boolean trackBean = EasyTrackUtil.trackBean(executorContext, entityClass);
//                if (trackBean) {
//
//                    TrackManager trackManager = executorContext.getRuntimeContext().getTrackManager();
//                    boolean hasTracked = trackManager.getCurrentTrackContext().hasTracked(entityClass);
//                    if (hasTracked) {
//                        for (T entity : entities) {
//                            trackManager.getCurrentTrackContext().removeTracking(entity);
//                        }
//                    }
//                }
//            }
//        }
//        return r;
//    }
//
//    @Override
//    public <T> int executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {
//        return 0;
//    }
//}
