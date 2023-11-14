//package com.easy.query.core.basic.jdbc.executor.sql;
//
//import com.easy.query.core.basic.extension.conversion.ValueConverter;
//import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
//import com.easy.query.core.basic.jdbc.conn.EasyConnection;
//import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
//import com.easy.query.core.basic.jdbc.parameter.BeanSQLParameter;
//import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
//import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
//import com.easy.query.core.basic.jdbc.parameter.SQLLikeParameter;
//import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
//import com.easy.query.core.basic.jdbc.types.EasyParameter;
//import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
//import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
//import com.easy.query.core.exception.EasyQueryException;
//import com.easy.query.core.logging.Log;
//import com.easy.query.core.logging.LogFactory;
//import com.easy.query.core.metadata.ColumnMetadata;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.util.EasyClassUtil;
//import com.easy.query.core.util.EasyCollectionUtil;
//import com.easy.query.core.util.EasyJdbcExecutorUtil;
//import com.easy.query.core.util.EasyObjectUtil;
//import com.easy.query.core.util.EasySQLUtil;
//import com.easy.query.core.util.EasyStringUtil;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * create time 2023/11/14 17:08
// * 文件说明
// *
// * @author xuejiaming
// */
//public abstract class AbstractBaseJdbcSQLExecutor implements JdbcSQLExecutor {
//    protected final int BATCH_GROUP_COUNT = 1000;
//    protected final Log log = LogFactory.getLog(EasyJdbcExecutorUtil.class);
//
//    protected void printShardingSQLFormat(final StringBuilder printSQL, final EasyConnection easyConnection) {
//        printSQL.append(Thread.currentThread().getName());
//        printSQL.append(", name:");
//        printSQL.append(easyConnection.getDataSourceName());
//        printSQL.append(", ");
//    }
//
//    protected void printReplicaSQLFormat(final StringBuilder printSQL, final EasyConnection easyConnection) {
//        printSQL.append("strategy:");
//        printSQL.append(easyConnection.getConnectionStrategy().getName());
//        printSQL.append(", ");
//    }
//
//    protected void logSQL(final boolean printSql, final String sql, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
//        if (printSql) {
//            StringBuilder printSQL = new StringBuilder();
//            printSQL.append("==> ");
//            if (shardingPrint) {
//                printShardingSQLFormat(printSQL, easyConnection);
//            }
//            if (replicaPrint) {
//                printReplicaSQLFormat(printSQL, easyConnection);
//            }
//            printSQL.append("Preparing: ");
//            printSQL.append(sql);
//            log.info(printSQL.toString());
//        }
//    }
//
//    protected void logParameter(boolean printSql, List<SQLParameter> parameters, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
//        if (printSql) {
//            StringBuilder printSQL = new StringBuilder();
//            printSQL.append("==> ");
//            if (shardingPrint) {
//                printShardingSQLFormat(printSQL, easyConnection);
//            }
//            if (replicaPrint) {
//                printReplicaSQLFormat(printSQL, easyConnection);
//            }
//            printSQL.append("Parameters: ");
//            printSQL.append(EasySQLUtil.sqlParameterToString(parameters));
//            log.info(printSQL.toString());
//        }
//    }
//
//    protected void logResult(boolean printSql, long total, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
//        if (printSql) {
//            StringBuilder printSQL = new StringBuilder();
//            printSQL.append("<== ");
//            if (shardingPrint) {
//                printShardingSQLFormat(printSQL, easyConnection);
//            }
//            if (replicaPrint) {
//                printReplicaSQLFormat(printSQL, easyConnection);
//            }
//            printSQL.append("Total: ");
//            printSQL.append(total);
//            log.info(printSQL.toString());
//        }
//    }
//
//    protected void logResult(boolean printSql, int total, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
//        if (printSql) {
//            logResult(true, (long) total, easyConnection, shardingPrint, replicaPrint);
//        }
//    }
//
//    protected void logUse(boolean printSql, long start, long end, final EasyConnection easyConnection, final boolean shardingPrint, final boolean replicaPrint) {
//        if (printSql) {
//            StringBuilder printSQL = new StringBuilder();
//            printSQL.append("<== ");
//            if (shardingPrint) {
//                printShardingSQLFormat(printSQL, easyConnection);
//            }
//            if (replicaPrint) {
//                printReplicaSQLFormat(printSQL, easyConnection);
//            }
//            printSQL.append("Time Elapsed: ");
//            printSQL.append(end - start);
//            printSQL.append("(ms)");
//            log.info(printSQL.toString());
//        }
//    }
//
//    /**
//     * 解压执行参数
//     *
//     * @param entity
//     * @param sqlParameters
//     * @param printSql
//     * @param easyConnection
//     * @param shardingPrint
//     * @param replicaPrint
//     * @param <T>
//     * @return
//     */
//    protected <T> List<SQLParameter> extractParameters(T entity, List<SQLParameter> sqlParameters, boolean printSql, EasyConnection easyConnection, boolean shardingPrint, boolean replicaPrint) {
//        if (EasyCollectionUtil.isNotEmpty(sqlParameters)) {
//
//            List<SQLParameter> params = new ArrayList<>(sqlParameters.size());
//            for (SQLParameter sqlParameter : sqlParameters) {
//                if (sqlParameter instanceof ConstSQLParameter) {
//                    Object value = toValue(sqlParameter, sqlParameter.getValue());
//                    params.add(new EasyConstSQLParameter(sqlParameter.getTableOrNull(), sqlParameter.getPropertyNameOrNull(), value));
//                } else if (sqlParameter instanceof BeanSQLParameter) {
//                    BeanSQLParameter beanSQLParameter = (BeanSQLParameter) sqlParameter;
//                    beanSQLParameter.setBean(entity);
//                    Object value = toValue(beanSQLParameter, beanSQLParameter.getValue());
//                    params.add(new EasyConstSQLParameter(beanSQLParameter.getTableOrNull(), beanSQLParameter.getPropertyNameOrNull(), value));
//                } else {
//                    throw new EasyQueryException("current sql parameter:[" + EasyClassUtil.getSimpleName(sqlParameter.getClass()) + "],property name:[" + sqlParameter.getPropertyNameOrNull() + "] is not implements BeanSQLParameter or ConstSQLParameter");
//                }
//            }
//
//            if (printSql) {
//                logParameter(true, params, easyConnection, shardingPrint, replicaPrint);
//            }
//            return params;
//        }
//        return Collections.emptyList();
//    }
//
//
//    protected PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {
//        return createPreparedStatement(connection, sql, sqlParameters, easyJdbcTypeHandlerManager, null);
//    }
//
//    protected PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager, List<String> generatedKeyColumns) throws SQLException {
//        PreparedStatement preparedStatement = EasyCollectionUtil.isEmpty(generatedKeyColumns) ? connection.prepareStatement(sql) : connection.prepareStatement(sql, generatedKeyColumns.toArray(new String[0]));
//        return setPreparedStatement(preparedStatement, sqlParameters, easyJdbcTypeHandlerManager);
//    }
//
//    protected PreparedStatement setPreparedStatement(PreparedStatement preparedStatement, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {
//        if (EasyCollectionUtil.isNotEmpty(sqlParameters)) {
//
//            EasyParameter easyParameter = new EasyParameter(preparedStatement, sqlParameters);
//            int paramSize = sqlParameters.size();
//            for (int i = 0; i < paramSize; i++) {
//                easyParameter.setIndex(i);
//                JdbcTypeHandler handler = easyJdbcTypeHandlerManager.getHandler(easyParameter.getValueType());
//                handler.setParameter(easyParameter);
//            }
//        }
//        return preparedStatement;
//    }
//
//
//    /**
//     * 如果当前value存在加密字段那么会自动解密
//     * 数据库转属性
//     *
//     * @param resultColumnMetadata
//     * @param value
//     * @return
//     */
//    protected Object fromValue(ResultColumnMetadata resultColumnMetadata, Object value) {
//        Class<?> entityClass = resultColumnMetadata.getEntityClass();
//        Object fromValue = fromValue0(entityClass, resultColumnMetadata, value);
//        return resultColumnMetadata.getValueConverter().deserialize(EasyObjectUtil.typeCast(fromValue), resultColumnMetadata.getColumnMetadata());
//    }
//
//    protected Object fromValue0(Class<?> entityClass, ResultColumnMetadata resultColumnMetadata, Object value) {
//        if (resultColumnMetadata.isEncryption()) {
//            EncryptionStrategy easyEncryptionStrategy = resultColumnMetadata.getEncryptionStrategy();
//            return easyEncryptionStrategy.decrypt(entityClass, resultColumnMetadata.getPropertyName(), value);
//        }
//        return value;
//    }
//
//    protected Object toValue(SQLParameter sqlParameter, Object value) {
//        if (sqlParameter.getTableOrNull() != null) {
//            EntityMetadata entityMetadata = sqlParameter.getTableOrNull().getEntityMetadata();
//            String propertyName = sqlParameter.getPropertyNameOrNull();
//            if (propertyName != null) {
//                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
//                ValueConverter<?, ?> valueConverter = columnMetadata.getValueConverter();
//                if (value != null) {
//                    Object toValue = toValue(columnMetadata, sqlParameter, value, entityMetadata.getEntityClass(), propertyName);
//                    return valueConverter.serialize(EasyObjectUtil.typeCast(toValue), columnMetadata);
//                }
//                return valueConverter.serialize(null, columnMetadata);
//            }
//        }
//        return value;
//    }
//
//    protected Object toValue(ColumnMetadata columnMetadata, SQLParameter sqlParameter, Object value, Class<?> entityClass, String propertyName) {
//
//        if (columnMetadata.isEncryption()) {
//            if (sqlParameter instanceof SQLLikeParameter) {
//                if (columnMetadata.isSupportQueryLike()) {
//                    EncryptionStrategy easyEncryptionStrategy = columnMetadata.getEncryptionStrategy();
//                    String likeValue = value.toString();
//                    String encryptValue = EasyStringUtil.endWithRemove(EasyStringUtil.startWithRemove(likeValue, "%"), "%");
//                    return EasyStringUtil.startWithDefault(likeValue, "%", EasyStringUtil.EMPTY) + easyEncryptionStrategy.encrypt(entityClass, propertyName, encryptValue) + EasyStringUtil.endWithDefault(likeValue, "%", EasyStringUtil.EMPTY);
//                }
//            } else {
//                EncryptionStrategy easyEncryptionStrategy = columnMetadata.getEncryptionStrategy();
//                return easyEncryptionStrategy.encrypt(entityClass, propertyName, value);
//            }
//        }
//        return value;
//    }
//
//    protected void clear(PreparedStatement ps) {
//        try {
//            if (ps != null) {
//                ps.close();
//            }
//        } catch (SQLException ignored) {
//        }
//    }
//
//    private static final int EXECUTE_DEFAULT_EFFECT=0;
//    protected int execute(boolean batch,int batchSize,PreparedStatement ps) throws SQLException {
//        if(batch){
//            ps.addBatch();
//            if ((batchSize % BATCH_GROUP_COUNT) == 0) {
//                int[] ints = ps.executeBatch();
//                ps.clearBatch();
//                return EasyCollectionUtil.sum(ints);
//            }
//            return EXECUTE_DEFAULT_EFFECT;
//        }else{
//            return ps.executeUpdate();
//        }
//    }
//}
