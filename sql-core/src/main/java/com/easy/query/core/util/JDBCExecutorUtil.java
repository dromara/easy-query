package com.easy.query.core.util;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.BeanSqlParameter;
import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.merge.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.sharding.merge.executor.internal.ExecuteResult;
import com.easy.query.core.sharding.merge.executor.internal.QueryExecuteResult;
import com.easy.query.core.sharding.merge.impl.DefaultStreamResult;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/21 17:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class JDBCExecutorUtil {

    private static  final Log log= LogFactory.getLog(JDBCExecutorUtil.class);

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
        boolean logDebug = log.isDebugEnabled();
        if (logDebug) {
            log.debug("==> Preparing: " + sql);
        }
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        EasyJdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandlerManager();

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SQLParameter> parameters = extractParameters(executorContext, null, sqlParameters);
        if (logDebug && ArrayUtil.isNotEmpty(parameters)) {
            log.debug("==> Parameters: " + SQLUtil.sqlParameterToString(parameters));
        }
        try {
            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler);
            if(logDebug){
                long start = System.currentTimeMillis();
                rs = ps.executeQuery();
                long end = System.currentTimeMillis();
                log.debug("<== Query Use: "+(end-start)+"(ms)");
            }else{
                rs = ps.executeQuery();
            }

        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLException(sql, e);
        }
        return new QueryExecuteResult(new DefaultStreamResult(rs),ps);
    }

    public static AffectedRowsExecuteResult insert(ExecutorContext executorContext, EasyConnection easyConnection, String sql, List<Object> entities, List<SQLParameter> sqlParameters, boolean fillAutoIncrement) {
        boolean logDebug = log.isDebugEnabled();
        if (logDebug) {
            log.debug("==> Preparing: " + sql);
        }
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        EasyJdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandlerManager();
        Class<?> entityClass = entities.get(0).getClass();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
        List<String> incrementColumns = fillAutoIncrement ? entityMetadata.getIncrementColumns() : null;
        PreparedStatement ps = null;
        int r = 0;
        boolean hasParameter = !sqlParameters.isEmpty();
        try {
            for (Object entity : entities) {
                List<SQLParameter> parameters = extractParameters(executorContext, entity, sqlParameters);
                if (logDebug && hasParameter) {
                    log.debug("==> Parameters: " + SQLUtil.sqlParameterToString(parameters));
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

            if (logDebug) {
                log.debug("<== Total: " + r);
            }
            //如果需要自动填充并且存在自动填充列
            if (fillAutoIncrement && ArrayUtil.isNotEmpty(incrementColumns)) {
                ResultSet keysSet = ps.getGeneratedKeys();
                int index = 0;
                PropertyDescriptor[] incrementProperty = new PropertyDescriptor[incrementColumns.size()];
                FastBean beanFastSetter = EasyUtil.getFastBean(entityClass);
                while (keysSet.next()) {
                    Object entity = entities.get(index);
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
        }
        return new AffectedRowsExecuteResult(r,ps);

    }

    private static PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {
        return createPreparedStatement(connection, sql, sqlParameters, easyJdbcTypeHandlerManager, null);
    }

    private static PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager, List<String> incrementColumns) throws SQLException {
        PreparedStatement preparedStatement = ArrayUtil.isEmpty(incrementColumns) ? connection.prepareStatement(sql) : connection.prepareStatement(sql, incrementColumns.toArray(new String[0]));
        return setPreparedStatement(preparedStatement, sqlParameters, easyJdbcTypeHandlerManager);
    }

    private static PreparedStatement setPreparedStatement(PreparedStatement preparedStatement, List<SQLParameter> sqlParameters, EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {

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
