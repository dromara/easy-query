package com.easy.query.core.util;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecuteBeforeArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EasyShardingStreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EasyStreamResultSet;
import com.easy.query.core.basic.jdbc.parameter.BeanSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLLikeParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLRawParameter;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/21 17:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyJdbcExecutorUtil {

    private static final int BATCH_GROUP_COUNT = 1000;
    private static final int EXECUTE_DEFAULT_EFFECT = 0;
    private static final Log log = LogFactory.getLog(EasyJdbcExecutorUtil.class);

    private static void printShardingSQLFormat(final StringBuilder printSQL, final EasyConnection easyConnection) {
        printSQL.append(Thread.currentThread().getName());
        printSQL.append(", name:");
        printSQL.append(easyConnection.getDataSourceName());
        printSQL.append(", ");
    }

    private static void printReplicaSQLFormat(final StringBuilder printSQL, final EasyConnection easyConnection) {
        printSQL.append("strategy:");
        printSQL.append(easyConnection.getConnectionStrategy().getName());
        printSQL.append(", ");
    }

    private static void logSQL(final boolean printSql, final String sql, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
        if (printSql) {
            StringBuilder printSQL = new StringBuilder();
            printSQL.append("==> ");
            if (shardingPrint) {
                printShardingSQLFormat(printSQL, easyConnection);
            }
            if (replicaPrint) {
                printReplicaSQLFormat(printSQL, easyConnection);
            }
            printSQL.append("Preparing: ");
            printSQL.append(sql);
            log.info(printSQL.toString());
        }
    }

    private static void logParameter(boolean printSql, List<SQLParameter> parameters, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
        if (printSql) {
            StringBuilder printSQL = new StringBuilder();
            printSQL.append("==> ");
            if (shardingPrint) {
                printShardingSQLFormat(printSQL, easyConnection);
            }
            if (replicaPrint) {
                printReplicaSQLFormat(printSQL, easyConnection);
            }
            printSQL.append("Parameters: ");
            printSQL.append(EasySQLUtil.sqlParameterToString(parameters));
            log.info(printSQL.toString());
        }
    }

    private static void logResult(boolean printSql, long total, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
        if (printSql) {
            StringBuilder printSQL = new StringBuilder();
            printSQL.append("<== ");
            if (shardingPrint) {
                printShardingSQLFormat(printSQL, easyConnection);
            }
            if (replicaPrint) {
                printReplicaSQLFormat(printSQL, easyConnection);
            }
            printSQL.append("Total: ");
            printSQL.append(total);
            log.info(printSQL.toString());
        }
    }

    private static void logResult(boolean printSql, int total, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
        if (printSql) {
            logResult(true, (long) total, easyConnection, shardingPrint, replicaPrint);
        }
    }

    private static void logUse(boolean printSql, long start, long end, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
        if (printSql) {
            StringBuilder printSQL = new StringBuilder();
            printSQL.append("<== ");
            if (shardingPrint) {
                printShardingSQLFormat(printSQL, easyConnection);
            }
            if (replicaPrint) {
                printReplicaSQLFormat(printSQL, easyConnection);
            }
            printSQL.append("Time Elapsed: ");
            printSQL.append(end - start);
            printSQL.append("(ms)");
            log.info(printSQL.toString());
        }
    }

    /**
     * 解压执行参数
     *
     * @param entity
     * @param sqlParameters
     * @param printSql
     * @param easyConnection
     * @param shardingPrint
     * @param replicaPrint
     * @param <T>
     * @return
     */
    public static <T> List<SQLParameter> extractParameters(T entity, List<SQLParameter> sqlParameters, boolean printSql, EasyConnection easyConnection, boolean shardingPrint, boolean replicaPrint) {
        if (EasyCollectionUtil.isNotEmpty(sqlParameters)) {

            List<SQLParameter> params = new ArrayList<>(sqlParameters.size());
            for (SQLParameter sqlParameter : sqlParameters) {
                if (sqlParameter instanceof ConstSQLParameter) {
                    Object value = toValue(sqlParameter, sqlParameter.getValue());
                    params.add(new EasyConstSQLParameter(sqlParameter.getTableOrNull(), sqlParameter.getPropertyNameOrNull(), value));
                } else if (sqlParameter instanceof BeanSQLParameter) {
                    BeanSQLParameter beanSQLParameter = (BeanSQLParameter) sqlParameter;
                    beanSQLParameter.setBean(entity);
                    Object value = toValue(beanSQLParameter, beanSQLParameter.getValue());
                    params.add(new EasyConstSQLParameter(beanSQLParameter.getTableOrNull(), beanSQLParameter.getPropertyNameOrNull(), value));
                } else {
                    throw new EasyQueryException("current sql parameter:[" + EasyClassUtil.getSimpleName(sqlParameter.getClass()) + "],property name:[" + sqlParameter.getPropertyNameOrNull() + "] is not implements BeanSQLParameter or ConstSQLParameter");
                }
            }

            if (printSql) {
                logParameter(true, params, easyConnection, shardingPrint, replicaPrint);
            }
            return params;
        }
        return Collections.emptyList();
    }

    public static StreamResultSet query(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters) throws SQLException {
        return query(executorContext, easyConnection, sql, sqlParameters, false, false);
    }

    public static StreamResultSet query(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {

        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getJdbcTypeHandlerManager();
        boolean printSql = executorContext.getEasyQueryOption().isPrintSql();
        logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
        boolean listen = jdbcExecutorListener.enable();
        SQLConsumer<Statement> configurer = executorContext.getConfigurer(shardingPrint);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SQLParameter> parameters = extractParameters(null, sqlParameters, printSql, easyConnection, shardingPrint, replicaPrint);

        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
        StreamResultSet sr = null;
        Exception exception = null;
        try {
            if (listen) {
                String traceId = jdbcExecutorListener.createTraceId();
                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, Collections.singletonList(parameters));
                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
            }
            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler);

            long start = printSql ? System.currentTimeMillis() : 0L;
            if (configurer != null) {
                configurer.accept(ps);
            }
            rs = ps.executeQuery();
            long end = printSql ? System.currentTimeMillis() : 0L;
            if (printSql) {
                logUse(true, start, end, easyConnection, shardingPrint, replicaPrint);
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
            log.error(sql, e);
            if (e instanceof SQLException) {
                throw new EasyQuerySQLStatementException(sql, e);
            } else {
                throw e;
            }
        } finally {
            if (listen) {
                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, 0, exception));
            }
        }
        return sr;
    }

    public static <T> int insert(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean fillAutoIncrement, boolean shardingPrint, boolean replicaPrint) throws SQLException {

        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
        boolean listen = jdbcExecutorListener.enable();
        boolean printSql = executorContext.getEasyQueryOption().isPrintSql();
        logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);

        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getJdbcTypeHandlerManager();
        Class<?> entityClass = entities.get(0).getClass();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
        List<String> generatedKeyColumns = fillAutoIncrement ? entityMetadata.getGeneratedKeyColumns() : null;
        PreparedStatement ps = null;
        Exception exception = null;
        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
        int r = 0;
        try {
            if (listen) {
                String traceId = jdbcExecutorListener.createTraceId();
                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, new ArrayList<>(entities.size()));
                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
            }
            int batchSize = 0;
            for (T entity : entities) {
                batchSize++;
                List<SQLParameter> parameters = extractParameters(entity, sqlParameters, printSql, easyConnection, shardingPrint, replicaPrint);
                if (listen) {
                    jdbcListenBeforeArg.getSqlParameters().add(parameters);
                }
                if (ps == null) {
                    ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler, generatedKeyColumns);
                } else {
                    setPreparedStatement(ps, parameters, easyJdbcTypeHandler);
                }
                r += execute(entities.size() > 1, batchSize, ps, (params) -> {
                    incrementBackFill(fillAutoIncrement, params.getAlreadyCommitSize(), generatedKeyColumns, params.getPs(), entities, entityMetadata);
                });
            }
            r += executeEnd(entities.size() > 1, batchSize, ps, (params) -> {
                incrementBackFill(fillAutoIncrement, params.getAlreadyCommitSize(), generatedKeyColumns, params.getPs(), entities, entityMetadata);
            });
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
            logResult(printSql, r, easyConnection, shardingPrint, replicaPrint);
        } catch (Exception e) {
            exception = e;
            log.error(sql, e);
            if (e instanceof SQLException) {
                throw new EasyQuerySQLStatementException(sql, e);
            } else {
                throw e;
            }
        } finally {
            clear(ps);
            if (listen) {
                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, r, exception));
            }
        }
        return r;

    }

    private static void clear(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ignored) {
        }
    }

    public static <T> int executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {
        boolean printSql = executorContext.getEasyQueryOption().isPrintSql();
        logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
        boolean listen = jdbcExecutorListener.enable();
        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getJdbcTypeHandlerManager();
        PreparedStatement ps = null;
        Exception exception = null;
        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
        int r = 0;
        try {
            if (listen) {
                String traceId = jdbcExecutorListener.createTraceId();
                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, new ArrayList<>(entities.size()));
                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
            }
            int batchSize = 0;
            for (T entity : entities) {
                batchSize++;
                List<SQLParameter> parameters = extractParameters(entity, sqlParameters, printSql, easyConnection, shardingPrint, replicaPrint);
                if (listen) {
                    jdbcListenBeforeArg.getSqlParameters().add(parameters);
                }
                if (ps == null) {
                    ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
                } else {
                    setPreparedStatement(ps, parameters, easyJdbcTypeHandlerManager);
                }
                r += execute(entities.size() > 1, batchSize, ps, null);
            }
            r += executeEnd(entities.size() > 1, batchSize, ps, null);
            logResult(printSql, r, easyConnection, shardingPrint, replicaPrint);
        } catch (Exception e) {
            exception = e;
            log.error(sql, e);
            if (e instanceof SQLException) {
                throw new EasyQuerySQLStatementException(sql, e);
            } else {
                throw e;
            }
        } finally {
            clear(ps);
            if (listen) {
                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, r, exception));
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

    public static <T> int executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters, boolean shardingPrint, boolean replicaPrint) throws SQLException {
        boolean printSql = executorContext.getEasyQueryOption().isPrintSql();
        logSQL(printSql, sql, easyConnection, shardingPrint, replicaPrint);
        QueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getJdbcTypeHandlerManager();

        List<SQLParameter> parameters = extractParameters(null, sqlParameters, printSql, easyConnection, shardingPrint, replicaPrint);

        JdbcExecutorListener jdbcExecutorListener = runtimeContext.getJdbcExecutorListener();
        boolean listen = jdbcExecutorListener.enable();
        JdbcExecuteBeforeArg jdbcListenBeforeArg = null;
        PreparedStatement ps = null;
        Exception exception = null;
        int r = 0;
        try {
            if (listen) {
                String traceId = jdbcExecutorListener.createTraceId();
                jdbcListenBeforeArg = new JdbcExecuteBeforeArg(traceId, sql, Collections.singletonList(parameters));
                jdbcExecutorListener.onExecuteBefore(jdbcListenBeforeArg);
            }
            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
            r = ps.executeUpdate();
            logResult(printSql, r, easyConnection, shardingPrint, replicaPrint);
        } catch (Exception e) {
            exception = e;
            log.error(sql, e);
            if (e instanceof SQLException) {
                throw new EasyQuerySQLStatementException(sql, e);
            } else {
                throw e;
            }
        } finally {
            clear(ps);
            if (listen) {
                jdbcExecutorListener.onExecuteAfter(new JdbcExecuteAfterArg(jdbcListenBeforeArg, r, exception));
            }
        }
        return r;
    }

    private static PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {
        return createPreparedStatement(connection, sql, sqlParameters, easyJdbcTypeHandlerManager, null);
    }

    private static PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager, List<String> generatedKeyColumns) throws SQLException {
        PreparedStatement preparedStatement = EasyCollectionUtil.isEmpty(generatedKeyColumns) ? connection.prepareStatement(sql) : connection.prepareStatement(sql, generatedKeyColumns.toArray(new String[0]));
        return setPreparedStatement(preparedStatement, sqlParameters, easyJdbcTypeHandlerManager);
    }

    private static PreparedStatement setPreparedStatement(PreparedStatement preparedStatement, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {
        if (EasyCollectionUtil.isNotEmpty(sqlParameters)) {

            EasyParameter easyParameter = new EasyParameter(preparedStatement, sqlParameters);
            int paramSize = sqlParameters.size();
            for (int i = 0; i < paramSize; i++) {
                easyParameter.setIndex(i);
                JdbcTypeHandler handler = easyJdbcTypeHandlerManager.getHandler(easyParameter.getValueType());
                handler.setParameter(easyParameter);
            }
        }
        return preparedStatement;
    }


    /**
     * 如果当前value存在加密字段那么会自动解密
     * 数据库转属性
     *
     * @param resultColumnMetadata
     * @param value
     * @return
     */
    public static Object fromValue(ResultColumnMetadata resultColumnMetadata, Object value) {
        Class<?> entityClass = resultColumnMetadata.getEntityClass();
        Object fromValue = fromValue0(entityClass, resultColumnMetadata, value);
        return resultColumnMetadata.getValueConverter().deserialize(EasyObjectUtil.typeCast(fromValue), resultColumnMetadata.getColumnMetadata());
    }

    private static Object fromValue0(Class<?> entityClass, ResultColumnMetadata resultColumnMetadata, Object value) {
        if (resultColumnMetadata.isEncryption()) {
            EncryptionStrategy easyEncryptionStrategy = resultColumnMetadata.getEncryptionStrategy();
            return easyEncryptionStrategy.decrypt(entityClass, resultColumnMetadata.getPropertyName(), value);
        }
        return value;
    }

    public static Object toValue(SQLParameter sqlParameter, Object value) {
        if(value instanceof SQLRawParameter){
            return ((SQLRawParameter)value).getVal();
        }
        if (sqlParameter.getTableOrNull() != null) {
            EntityMetadata entityMetadata = sqlParameter.getTableOrNull().getEntityMetadata();
            String propertyName = sqlParameter.getPropertyNameOrNull();
            if (propertyName != null) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                ValueConverter<?, ?> valueConverter = columnMetadata.getValueConverter();
                if (value != null) {
                    Object toValue = toValue(columnMetadata, sqlParameter, value, entityMetadata.getEntityClass(), propertyName);
                    return valueConverter.serialize(EasyObjectUtil.typeCast(toValue), columnMetadata);
                }
                return valueConverter.serialize(null, columnMetadata);
            }
        }
        return value;
    }

    private static Object toValue(ColumnMetadata columnMetadata, SQLParameter sqlParameter, Object value, Class<?> entityClass, String propertyName) {

        if (columnMetadata.isEncryption()) {
            if (sqlParameter instanceof SQLLikeParameter) {
                if (columnMetadata.isSupportQueryLike()) {
                    EncryptionStrategy easyEncryptionStrategy = columnMetadata.getEncryptionStrategy();
                    String likeValue = value.toString();
                    String encryptValue = EasyStringUtil.endWithRemove(EasyStringUtil.startWithRemove(likeValue, "%"), "%");
                    return EasyStringUtil.startWithDefault(likeValue, "%", EasyStringUtil.EMPTY) + easyEncryptionStrategy.encrypt(entityClass, propertyName, encryptValue) + EasyStringUtil.endWithDefault(likeValue, "%", EasyStringUtil.EMPTY);
                }
            } else {
                EncryptionStrategy easyEncryptionStrategy = columnMetadata.getEncryptionStrategy();
                return easyEncryptionStrategy.encrypt(entityClass, propertyName, value);
            }
        }
        return value;
    }

    private static int execute(boolean batch, int batchSize, PreparedStatement ps, @Nullable SQLConsumer<InsertBackFillParams> consumer) throws SQLException {
        if (batch) {
            ps.addBatch();
            if ((batchSize % BATCH_GROUP_COUNT) == 0) {
                int[] ints = ps.executeBatch();
                ps.clearBatch();
                if (consumer != null) {
                    consumer.accept(new InsertBackFillParams(batchSize, ps));
                }
                return EasyCollectionUtil.sum(ints);
            }
            return EXECUTE_DEFAULT_EFFECT;
        } else {
            return ps.executeUpdate();
        }
    }

    private static int executeEnd(boolean batch, int batchSize, PreparedStatement ps, @Nullable SQLConsumer<InsertBackFillParams> consumer) throws SQLException {
        if (batch) {
            if ((batchSize % BATCH_GROUP_COUNT) != 0) {
                int[] ints = ps.executeBatch();
                ps.clearBatch();
                if (consumer != null) {
                    consumer.accept(new InsertBackFillParams(batchSize, ps));
                }
                return EasyCollectionUtil.sum(ints);
            }
        }else {
            if (consumer != null) {
                consumer.accept(new InsertBackFillParams(1, ps));
            }
        }
        return EXECUTE_DEFAULT_EFFECT;
    }


    private static <T> void incrementBackFill(boolean fillAutoIncrement, int alreadyCommitSize, List<String> generatedKeyColumns, PreparedStatement ps, List<T> entities, EntityMetadata entityMetadata) throws SQLException {
        if (fillAutoIncrement && EasyCollectionUtil.isNotEmpty(generatedKeyColumns)) {
            assert ps != null;
            ResultSet keysSet = ps.getGeneratedKeys();
            //未提交数量
            int unCommitSize = alreadyCommitSize % BATCH_GROUP_COUNT;
            int index = unCommitSize == 0 ? alreadyCommitSize - BATCH_GROUP_COUNT : alreadyCommitSize - unCommitSize;
            ColumnMetadata[] columnMetadatas = new ColumnMetadata[generatedKeyColumns.size()];
            while (keysSet.next()) {
                T entity = entities.get(index);
                for (int i = 0; i < generatedKeyColumns.size(); i++) {
                    ColumnMetadata columnMetadata = columnMetadatas[i];
                    if (columnMetadata == null) {
                        String columnName = generatedKeyColumns.get(i);
                        String propertyName = entityMetadata.getPropertyNameNotNull(columnName);
                        columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                        columnMetadatas[i] = columnMetadata;
                    }

                    Object value = keysSet.getObject(i + 1);
                    Object newValue = EasyClassUtil.convertValueToRequiredType(value, columnMetadata.getPropertyType());
                    PropertySetterCaller<Object> beanSetter = columnMetadata.getSetterCaller();
                    beanSetter.call(entity, newValue);
//                        Method setter = getSetter(property, entityClass);
//                        callSetter(entity,setter, property, newValue);
                }
                index++;
            }
        }
    }

    public static class InsertBackFillParams {
        private final int alreadyCommitSize;
        private final PreparedStatement ps;

        public InsertBackFillParams(int alreadyCommitSize, PreparedStatement ps) {

            this.alreadyCommitSize = alreadyCommitSize;
            this.ps = ps;
        }

        public int getAlreadyCommitSize() {
            return alreadyCommitSize;
        }

        public PreparedStatement getPs() {
            return ps;
        }
    }
}
