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
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.QueryExecuteResult;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.result.impl.EasyShardingStreamResult;
import com.easy.query.core.sharding.merge.result.impl.EasyStreamResult;

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

    private static void logSql(boolean logDebug, String sql) {
        if (logDebug) {
            log.debug("==> " + Thread.currentThread().getName() + " Preparing: " + sql);
        }
    }

    private static void logParameter(boolean logDebug, List<SQLParameter> parameters) {
        if (logDebug) {
            log.debug("==> " + Thread.currentThread().getName() + " Parameters: " + SQLUtil.sqlParameterToString(parameters));
        }
    }

    private static void logResult(boolean logDebug, long total) {
        if (logDebug) {
            log.debug("<== " + Thread.currentThread().getName() + " Total: " + total);
        }
    }

    private static void logResult(boolean logDebug, int total) {
        if (logDebug) {
            log.debug("<== " + Thread.currentThread().getName() + " Total: " + total);
        }
    }

    private static void logUse(boolean logDebug, long start, long end) {
        if (logDebug) {
            log.debug("<== " + Thread.currentThread().getName() + " Query Use: " + (end - start) + "(ms)");
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
    public static QueryExecuteResult query(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters){
        return query(executorContext,easyConnection,sql,sqlParameters,false);
    }
    public static QueryExecuteResult query(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters,boolean sharding) {
        boolean logDebug = log.isDebugEnabled();
        logSql(logDebug, sql);
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandlerManager();

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SQLParameter> parameters = extractParameters(executorContext, null, sqlParameters);
        if (logDebug && ArrayUtil.isNotEmpty(parameters)) {
            logParameter(true, parameters);
        }
        StreamResult sr=null;
        try {
            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler);
            if (logDebug) {
                long start = System.currentTimeMillis();
                rs = ps.executeQuery();
                long end = System.currentTimeMillis();
                logUse(true, start, end);
            } else {
                rs = ps.executeQuery();
            }
            //如果是分片查询那么需要提前next
            if(sharding){
                boolean next = rs.next();
                sr=new EasyShardingStreamResult(rs,next);
            }else{
                sr=new EasyStreamResult(rs);
            }

        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLException(sql, e);
        }
        return new QueryExecuteResult(sr, ps);
    }

    public static <T> AffectedRowsExecuteResult insert(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean fillAutoIncrement) {
        boolean logDebug = log.isDebugEnabled();
        logSql(logDebug, sql);
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
                    logParameter(true, parameters);
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
            logResult(logDebug, r);
            //如果需要自动填充并且存在自动填充列
            if (fillAutoIncrement && ArrayUtil.isNotEmpty(incrementColumns)) {
                ResultSet keysSet = ps.getGeneratedKeys();
                int index = 0;
                PropertyDescriptor[] incrementProperty = new PropertyDescriptor[incrementColumns.size()];
                FastBean beanFastSetter = EasyUtil.getFastBean(entityClass);
                while (keysSet.next()) {
                    T entity = entities.get(index);
                    for (int i = 0; i < incrementColumns.size(); i++) {
                        PropertyDescriptor property = incrementProperty[i];
                        if (property == null) {
                            String columnName = incrementColumns.get(i);
                            String propertyName = entityMetadata.getPropertyNameOrDefault(columnName);
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
            throw new EasyQuerySQLException(sql, e);
        } finally {
            clear(ps);
        }
        return new AffectedRowsExecuteResult(r);

    }

    private static void clear(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ignored) {
        }
    }

    public static <T> AffectedRowsExecuteResult executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<T> entities, List<SQLParameter> sqlParameters) {
        boolean logDebug = log.isDebugEnabled();
        logSql(logDebug, sql);
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getEasyJdbcTypeHandlerManager();
        PreparedStatement ps = null;
        int r = 0;
        boolean hasParameter = ArrayUtil.isNotEmpty(sqlParameters);
        try {
            for (T entity : entities) {

                List<SQLParameter> parameters = extractParameters(executorContext, entity, sqlParameters);

                if (logDebug && hasParameter) {
                    logParameter(true, parameters);
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
            r = ArrayUtil.sum(rs);
            logResult(logDebug, r);
            ps.clearBatch();
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLException(sql, e);
        } finally {
            clear(ps);
        }
        return new AffectedRowsExecuteResult(r);
    }

    public static <T> AffectedRowsExecuteResult executeRows(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<SQLParameter> sqlParameters) {
        boolean logDebug = log.isDebugEnabled();
        logSql(logDebug, sql);
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        JdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getEasyJdbcTypeHandlerManager();
        PreparedStatement ps = null;
        int r = 0;

        List<SQLParameter> parameters = extractParameters(executorContext, null, sqlParameters);
        if (logDebug && !parameters.isEmpty()) {
            logParameter(true, parameters);
        }
        try {
            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
            r = ps.executeUpdate();
            logResult(logDebug, r);
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLException(sql, e);
        } finally {
            clear(ps);
        }
        return new AffectedRowsExecuteResult(r);
    }


    private static PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {
        return createPreparedStatement(connection, sql, sqlParameters, easyJdbcTypeHandlerManager, null);
    }

    private static PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, JdbcTypeHandlerManager easyJdbcTypeHandlerManager, List<String> incrementColumns) throws SQLException {
        PreparedStatement preparedStatement = ArrayUtil.isEmpty(incrementColumns) ? connection.prepareStatement(sql) : connection.prepareStatement(sql, incrementColumns.toArray(new String[0]));
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
