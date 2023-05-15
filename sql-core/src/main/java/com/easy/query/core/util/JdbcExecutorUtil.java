package com.easy.query.core.util;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.BeanSqlParameter;
import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQuerySQLExecuteException;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EasyShardingStreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EasyStreamResultSet;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/21 17:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class JdbcExecutorUtil {

    private static final Log log = LogFactory.getLog(JdbcExecutorUtil.class);
    private static void printShardingSqlFormat(final StringBuilder printSql,final EasyConnection easyConnection){
        printSql.append(Thread.currentThread().getName());
        printSql.append(", name:");
        printSql.append(easyConnection.getDataSourceName());
        printSql.append(", ");
    }
    private static void printReplicaSqlFormat(final StringBuilder printSql,final EasyConnection easyConnection){
        printSql.append("strategy:");
        printSql.append(easyConnection.getConnectionStrategy().getName());
        printSql.append(", ");
    }

    private static void logSql(final boolean logDebug,final String sql,final EasyConnection easyConnection,final boolean shardingPrint,final boolean replicaPrint) {
        if (logDebug) {
            StringBuilder printSql = new StringBuilder();
            printSql.append("==> ");
            if(shardingPrint){
                printShardingSqlFormat(printSql,easyConnection);
            }
            if(replicaPrint){
                printReplicaSqlFormat(printSql,easyConnection);
            }
            printSql.append("Preparing: ");
            printSql.append(sql);
            log.debug(printSql.toString());
        }
    }

    private static void logParameter(boolean logDebug,List<SQLParameter> parameters,final EasyConnection easyConnection,final boolean shardingPrint,final boolean replicaPrint) {
        if (logDebug) {
            StringBuilder printSql = new StringBuilder();
            printSql.append("==> ");
            if(shardingPrint){
                printShardingSqlFormat(printSql,easyConnection);
            }
            if(replicaPrint){
                printReplicaSqlFormat(printSql,easyConnection);
            }
            printSql.append("Parameters: ");
            printSql.append(SqlUtil.sqlParameterToString(parameters));
            log.debug(printSql.toString());
        }
    }

    private static void logResult(boolean logDebug,long total,final EasyConnection easyConnection,final boolean shardingPrint,final boolean replicaPrint) {
        if (logDebug) {
            StringBuilder printSql = new StringBuilder();
            printSql.append("<== ");
            if(shardingPrint){
                printShardingSqlFormat(printSql,easyConnection);
            }
            if(replicaPrint){
                printReplicaSqlFormat(printSql,easyConnection);
            }
            printSql.append("Total: ");
            printSql.append(total);
            log.debug(printSql.toString());
        }
    }

    private static void logResult(boolean logDebug,int total,final EasyConnection easyConnection,final boolean shardingPrint,final boolean replicaPrint) {
        if (logDebug) {
             logResult(true,(long)total,easyConnection,shardingPrint,replicaPrint); }
    }

    private static void logUse(boolean logDebug,long start, long end,final EasyConnection easyConnection,final boolean shardingPrint,final boolean replicaPrint) {
        if (logDebug) {
            StringBuilder printSql = new StringBuilder();
            printSql.append("<== ");
            if(shardingPrint){
                printShardingSqlFormat(printSql,easyConnection);
            }
            if(replicaPrint){
                printReplicaSqlFormat(printSql,easyConnection);
            }
            printSql.append("Time Elapsed: ");
            printSql.append(end - start);
            printSql.append("(ms)");
            log.debug(printSql.toString());
        }
    }

    public static <T> List<SQLParameter> extractParameters(ExecutorContext executorContext, T entity, List<SQLParameter> sqlParameters) {
        List<SQLParameter> params = new ArrayList<>(sqlParameters.size());
        for (SQLParameter sqlParameter : sqlParameters) {
            if (sqlParameter instanceof ConstSQLParameter) {
                Object value = executorContext.getEncryptValue(sqlParameter, sqlParameter.getValue());
                params.add(new EasyConstSQLParameter(sqlParameter.getTable(), sqlParameter.getPropertyName(), value));
            } else if (sqlParameter instanceof BeanSqlParameter) {
                BeanSqlParameter beanSqlParameter = (BeanSqlParameter) sqlParameter;
                beanSqlParameter.setBean(entity);
                Object value = executorContext.getEncryptValue(beanSqlParameter, beanSqlParameter.getValue());
                params.add(new EasyConstSQLParameter(beanSqlParameter.getTable(), beanSqlParameter.getPropertyName(), value));
            } else {
                throw new EasyQueryException("current sql parameter:[" + ClassUtil.getSimpleName(sqlParameter.getClass()) + "],property name:[" + sqlParameter.getPropertyName() + "] is not implements BeanSqlParameter or ConstSQLParameter");
            }
        }
        return params;
    }
    public static StreamResultSet query(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters){
        return query(executorContext,easyConnection,sql,sqlParameters,false,false);
    }
    public static StreamResultSet query(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters,boolean shardingPrint,boolean replicaPrint) {
        boolean logDebug = log.isDebugEnabled();
        logSql(logDebug, sql,easyConnection,shardingPrint,replicaPrint);
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandlerManager();

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SQLParameter> parameters = extractParameters(executorContext, null, sqlParameters);
        if (logDebug && EasyCollectionUtil.isNotEmpty(parameters)) {
            logParameter(true, parameters,easyConnection,shardingPrint,replicaPrint);
        }
        StreamResultSet sr=null;
        try {
            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler);
            if (logDebug) {
                long start = System.currentTimeMillis();
                rs = ps.executeQuery();
                long end = System.currentTimeMillis();
                logUse(true, start, end,easyConnection,shardingPrint,replicaPrint);
            } else {
                rs = ps.executeQuery();
            }
            //如果是分片查询那么需要提前next
            if(shardingPrint){
                boolean next = rs.next();
                sr=new EasyShardingStreamResultSet(rs,ps,next);
            }else{
                sr=new EasyStreamResultSet(rs,ps);
            }

        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLExecuteException(sql, e);
        }
        return sr;
    }

    public static <T> int insert(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean fillAutoIncrement,boolean shardingPrint,boolean replicaPrint) {
        boolean logDebug = log.isDebugEnabled();
        logSql(logDebug, sql,easyConnection,shardingPrint,replicaPrint);
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandlerManager();
        Class<?> entityClass = entities.get(0).getClass();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
        List<String> incrementColumns = fillAutoIncrement ? entityMetadata.getIncrementColumns() : null;
        PreparedStatement ps = null;
        int r = 0;
        boolean hasParameter = !sqlParameters.isEmpty();
        try {
            for (T entity : entities) {
                List<SQLParameter> parameters = extractParameters(executorContext, entity, sqlParameters);
                if (logDebug && hasParameter) {
                    logParameter(true, parameters,easyConnection,shardingPrint,replicaPrint);
                }
                if (ps == null) {
                    ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler, incrementColumns);
                } else {
                    setPreparedStatement(ps, parameters, easyJdbcTypeHandler);
                }
                ps.addBatch();
            }
            assert ps != null;
            int[] rs = ps.executeBatch();
            r = rs.length;
            logResult(logDebug, r,easyConnection,shardingPrint,replicaPrint);
            //如果需要自动填充并且存在自动填充列
            if (fillAutoIncrement && EasyCollectionUtil.isNotEmpty(incrementColumns)) {
                ResultSet keysSet = ps.getGeneratedKeys();
                int index = 0;
                PropertyDescriptor[] incrementProperty = new PropertyDescriptor[incrementColumns.size()];
                FastBean beanFastSetter = BeanUtil.getFastBean(entityClass);
                while (keysSet.next()) {
                    T entity = entities.get(index);
                    for (int i = 0; i < incrementColumns.size(); i++) {
                        PropertyDescriptor property = incrementProperty[i];
                        if (property == null) {
                            String columnName = incrementColumns.get(i);
                            String propertyName = entityMetadata.getPropertyNameOrNull(columnName);
                            property = entityMetadata.getColumnNotNull(propertyName).getProperty();
                            incrementProperty[i] = property;
                        }

                        Object value = keysSet.getObject(i + 1);
                        Object newValue = ClassUtil.convertValueToRequiredType(value, property.getPropertyType());
                        PropertySetterCaller<Object> beanSetter = beanFastSetter.getBeanSetter(property);
                        beanSetter.call(entity, newValue);
//                        Method setter = getSetter(property, entityClass);
//                        callSetter(entity,setter, property, newValue);
                    }
                    index++;
                }
            }
            ps.clearBatch();
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLExecuteException(sql, e);
        } finally {
            clear(ps);
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

    public static <T> int executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters,boolean shardingPrint,boolean replicaPrint) {
        boolean logDebug = log.isDebugEnabled();
        logSql(logDebug, sql,easyConnection,shardingPrint,replicaPrint);
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getEasyJdbcTypeHandlerManager();
        PreparedStatement ps = null;
        int r = 0;
        boolean hasParameter = EasyCollectionUtil.isNotEmpty(sqlParameters);
        try {
            for (T entity : entities) {

                List<SQLParameter> parameters = extractParameters(executorContext, entity, sqlParameters);

                if (logDebug && hasParameter) {
                    logParameter(true, parameters,easyConnection,shardingPrint,replicaPrint);
                }
                if (ps == null) {
                    ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
                } else {
                    setPreparedStatement(ps, parameters, easyJdbcTypeHandlerManager);
                }
                ps.addBatch();
            }
            assert ps != null;
            int[] rs = ps.executeBatch();
            r = EasyCollectionUtil.sum(rs);
            logResult(logDebug, r,easyConnection,shardingPrint,replicaPrint);
            ps.clearBatch();
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLExecuteException(sql, e);
        } finally {
            clear(ps);
        }
        return r;
    }

    public static <T> int executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters,boolean shardingPrint,boolean replicaPrint) {
        boolean logDebug = log.isDebugEnabled();
        logSql(logDebug, sql,easyConnection,shardingPrint,replicaPrint);
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getEasyJdbcTypeHandlerManager();
        PreparedStatement ps = null;
        int r = 0;

        List<SQLParameter> parameters = extractParameters(executorContext, null, sqlParameters);
        if (logDebug && !parameters.isEmpty()) {
            logParameter(true, parameters,easyConnection,shardingPrint,replicaPrint);
        }
        try {
            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
            r = ps.executeUpdate();
            logResult(logDebug, r,easyConnection,shardingPrint,replicaPrint);
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLExecuteException(sql, e);
        } finally {
            clear(ps);
        }
        return r;
    }


    private static PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {
        return createPreparedStatement(connection, sql, sqlParameters, easyJdbcTypeHandlerManager, null);
    }

    private static PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager, List<String> incrementColumns) throws SQLException {
        PreparedStatement preparedStatement = EasyCollectionUtil.isEmpty(incrementColumns) ? connection.prepareStatement(sql) : connection.prepareStatement(sql, incrementColumns.toArray(new String[0]));
        return setPreparedStatement(preparedStatement, sqlParameters, easyJdbcTypeHandlerManager);
    }

    private static PreparedStatement setPreparedStatement(PreparedStatement preparedStatement, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {

        EasyParameter easyParameter = new EasyParameter(preparedStatement, sqlParameters);
        int paramSize = sqlParameters.size();
        for (int i = 0; i < paramSize; i++) {
            easyParameter.setIndex(i);
            JdbcTypeHandler handler = easyJdbcTypeHandlerManager.getHandler(easyParameter.getValueType());
            handler.setParameter(easyParameter);
        }
        return preparedStatement;
    }


}
