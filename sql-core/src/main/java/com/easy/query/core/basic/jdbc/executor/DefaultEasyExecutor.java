package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.metadata.ColumnMetadata;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.parameter.BeanSqlParameter;
import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.track.TrackManager;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.SQLUtil;
import com.easy.query.core.util.StringUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: DefaultExecutor.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:49
 * @Created by xuejiaming
 */
public class DefaultEasyExecutor implements EasyExecutor {
    private static final Log log = LogFactory.getLog(DefaultEasyExecutor.class);
    private final boolean logDebug;

    public DefaultEasyExecutor() {
        logDebug = log.isDebugEnabled();
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters) {

        if (logDebug) {
            log.debug("==> Preparing: " + sql);
            if (!sqlParameters.isEmpty()) {
                log.debug("==> Parameters: " + SQLUtil.sqlParameterToString(sqlParameters));
            }
        }
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        EasyConnectionManager connectionManager = runtimeContext.getConnectionManager();
        EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getEasyJdbcTypeHandlerManager();
        EasyConnection easyConnection = null;
        PreparedStatement ps = null;
        int r = 0;
        try {
            easyConnection = connectionManager.getEasyConnection();
            ps = createPreparedStatement(easyConnection.getConnection(), sql, sqlParameters, easyJdbcTypeHandlerManager);
            r = ps.executeUpdate();
            if (logDebug) {
                log.debug("<== Total: " + r);
            }
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLException(sql, e);
        } finally {
            clear(executorContext, easyConnection, null, ps);
        }
        return r;
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, String sql, List<T> entities, List<SQLParameter> sqlParameters) {
        if (logDebug) {
            log.debug("==> Preparing: " + sql);
        }
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        EasyConnectionManager connectionManager = runtimeContext.getConnectionManager();
        EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager = runtimeContext.getEasyJdbcTypeHandlerManager();
        EasyConnection easyConnection = null;
        PreparedStatement ps = null;
        int r = 0;
        boolean hasParameter = !sqlParameters.isEmpty();
        try {
            for (T entity : entities) {

                List<SQLParameter> parameters = extractParameters(entity, sqlParameters);

                if (logDebug && hasParameter) {
                    log.debug("==> Parameters: " + SQLUtil.sqlParameterToString(parameters));
                }
                if (easyConnection == null) {
                    easyConnection = connectionManager.getEasyConnection();
                }
                if (ps == null) {
                    ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandlerManager);
                } else {
                    setPreparedStatement(ps, parameters, easyJdbcTypeHandlerManager);
                }
                ps.addBatch();
            }
            int[] rs = ps.executeBatch();
            r = ArrayUtil.sum(rs);
            if (logDebug) {
                log.debug("<== Total: " + r);
            }
            ps.clearBatch();
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLException(sql, e);
        } finally {
            clear(executorContext, easyConnection, null, ps);
        }
        return r;
    }

    @Override
    public <T> long insert(ExecutorContext executorContext, String sql, List<T> entities, List<SQLParameter> sqlParameters, boolean fillAutoIncrement) {
        if (logDebug) {
            log.debug("==> Preparing: " + sql);
        }
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        EasyConnectionManager connectionManager = runtimeContext.getConnectionManager();
        EasyJdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandlerManager();
        Class<?> entityClass = entities.get(0).getClass();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
        List<String> incrementColumns = fillAutoIncrement ? entityMetadata.getIncrementColumns() : null;
        EasyConnection easyConnection = null;
        PreparedStatement ps = null;
        int r = 0;
        boolean hasParameter = !sqlParameters.isEmpty();
        try {
            for (T entity : entities) {
                List<SQLParameter> parameters = extractParameters(entity, sqlParameters);
                if (logDebug && hasParameter) {
                    log.debug("==> Parameters: " + SQLUtil.sqlParameterToString(parameters));
                }
                if (easyConnection == null) {
                    easyConnection = connectionManager.getEasyConnection();
                }
                if (ps == null) {
                    ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler, incrementColumns);
                } else {
                    setPreparedStatement(ps, parameters, easyJdbcTypeHandler);
                }
                ps.addBatch();
            }
            int[] rs = ps.executeBatch();
            r = rs.length;

            if (logDebug) {
                log.debug("<== Total: " + r);
            }
            //如果需要自动填充并且存在自动填充列
            if (fillAutoIncrement&&ArrayUtil.isNotEmpty(incrementColumns)) {
                ResultSet keysSet = ps.getGeneratedKeys();
                int index = 0;
                PropertyDescriptor[] incrementProperty = new PropertyDescriptor[incrementColumns.size()];
                while (keysSet.next()) {
                    T entity = entities.get(index);
                    for (int i = 0; i < incrementColumns.size(); i++) {
                        PropertyDescriptor property = incrementProperty[i];
                        if (property == null) {
                            String columnName = incrementColumns.get(i);
                            String propertyName = entityMetadata.getPropertyName(columnName);
                            property = entityMetadata.getColumnNotNull(propertyName).getProperty();
                            incrementProperty[i] = property;
                        }

                        Object value = keysSet.getObject(i + 1);
                        Object newValue = ClassUtil.convertValueToRequiredType(value, property.getPropertyType());
                        Method setter = getSetter(property, entityClass);
                        callSetter(entity,setter, property, newValue);
                    }
                    index++;
                }
            }
            ps.clearBatch();
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLException(sql, e);
        } finally {
            clear(executorContext, easyConnection, null, ps);
        }
        return r;
    }

    /**
     * 提取参数
     *
     * @param entity
     * @param sqlParameters
     * @param <T>
     * @return ConstSQLParameter 集合
     */
    private <T> List<SQLParameter> extractParameters(T entity, List<SQLParameter> sqlParameters) {
        List<SQLParameter> params = new ArrayList<>(sqlParameters.size());
        for (SQLParameter sqlParameter : sqlParameters) {
            if (sqlParameter instanceof ConstSQLParameter) {
                params.add(sqlParameter);
            } else if (sqlParameter instanceof BeanSqlParameter) {
                BeanSqlParameter beanSqlParameter = (BeanSqlParameter) sqlParameter;
                beanSqlParameter.setBean(entity);
                Object value = beanSqlParameter.getValue();
                params.add(new ConstSQLParameter(beanSqlParameter.getTable(), beanSqlParameter.getPropertyName(), value));
            } else {
                throw new EasyQueryException("current sql parameter:[" + ClassUtil.getSimpleName(sqlParameter.getClass()) + "],property name:[" + sqlParameter.getPropertyName() + "] is not implements BeanSqlParameter or ConstSQLParameter");
            }
        }
        return params;
    }

    @Override
    public <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, String sql, List<SQLParameter> sqlParameters) {

        if (logDebug) {
            log.debug("==> Preparing: " + sql);
            if (!sqlParameters.isEmpty()) {
                log.debug("==> Parameters: " + SQLUtil.sqlParameterToString(sqlParameters));
            }
        }
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        EasyConnectionManager connectionManager = runtimeContext.getConnectionManager();
        EasyJdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandlerManager();
        List<TR> result = null;
        EasyConnection easyConnection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            easyConnection = connectionManager.getEasyConnection();
            ps = createPreparedStatement(easyConnection.getConnection(), sql, sqlParameters, easyJdbcTypeHandler);
            rs = ps.executeQuery();
            result = mapTo(executorContext, rs, clazz);

            if (logDebug) {
                log.debug("<== Total: " + result.size());
            }
        } catch (SQLException e) {
            log.error(sql, e);
            throw new EasyQuerySQLException(sql, e);
        } finally {
            clear(executorContext, easyConnection, rs, ps);
        }
        return result;
    }

    protected void clear(ExecutorContext executorContext, EasyConnection easyConnection, ResultSet rs, PreparedStatement ps) {
        EasyConnectionManager connectionManager = executorContext.getRuntimeContext().getConnectionManager();
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            connectionManager.closeEasyConnection(easyConnection);
        } catch (SQLException ignored) {
        }
    }

    private PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {
        return createPreparedStatement(connection, sql, sqlParameters, easyJdbcTypeHandlerManager, null);
    }

    private PreparedStatement createPreparedStatement(Connection connection, String sql, List<SQLParameter> sqlParameters, EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager, List<String> incrementColumns) throws SQLException {
        PreparedStatement preparedStatement = ArrayUtil.isEmpty(incrementColumns) ? connection.prepareStatement(sql) : connection.prepareStatement(sql, incrementColumns.toArray(new String[0]));
        return setPreparedStatement(preparedStatement, sqlParameters, easyJdbcTypeHandlerManager);
    }

    private PreparedStatement setPreparedStatement(PreparedStatement preparedStatement, List<SQLParameter> sqlParameters, EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager) throws SQLException {

        EasyParameter easyParameter = new EasyParameter(preparedStatement, sqlParameters);
        int paramSize = sqlParameters.size();
        for (int i = 0; i < paramSize; i++) {
            easyParameter.setIndex(i);
            JdbcTypeHandler handler = easyJdbcTypeHandlerManager.getHandler(easyParameter.getValueType());
            handler.setParameter(easyParameter);
        }
        return preparedStatement;
    }

    private <T> List<T> mapTo(ExecutorContext context, ResultSet rs, Class<T> clazz) throws SQLException {
        List<T> resultList = null;
        if (Map.class.isAssignableFrom(clazz)) {
            throw new EasyQueryException("不支持：" + ClassUtil.getSimpleName(clazz) + "的转换");
        } else if (ClassUtil.isBasicType(clazz)) {//如果返回的是基本类型
            resultList = new ArrayList<>(1);
            while (rs.next()) {
                Object value = mapToBasic(context, rs, clazz);
                resultList.add((T) value);
            }
        } else {
            resultList = mapToBeans(context, rs, clazz);
        }
        return resultList;
    }

    private <T> PropertyDescriptor[] columnsToProperties(ExecutorContext context, ResultSet rs, ResultSetMetaData rsmd, Class<T> clazz) throws SQLException {

        EntityMetadataManager entityMetadataManager = context.getRuntimeContext().getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(clazz);
        //需要返回的结果集映射到bean实体上
        //int[] 索引代表数据库返回的索引，数组索引所在的值代表属性数组的对应属性
        int columnCount = rsmd.getColumnCount();//有多少列
        PropertyDescriptor[] columnToProperty = new PropertyDescriptor[columnCount];
        for (int i = 0; i < columnCount; i++) {

            String colName = getColName(rsmd, i + 1);//数据库查询出来的列名

            String propertyName = entityMetadata.getPropertyName(colName);
            ColumnMetadata column = entityMetadata.getColumnOrNull(propertyName);
            if (column != null) {
                columnToProperty[i] = column.getProperty();
            } else {
                columnToProperty[i] = null;
            }
        }
        return columnToProperty;
    }

    private <T> Object mapToBasic(ExecutorContext context, ResultSet rs, Class<T> clazz) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (columnCount != 1) {
            throw new SQLException("返回类型:" + clazz + ",期望返回一列");
        }
        EasyResultSet easyResultSet = new EasyResultSet(rs);
        easyResultSet.setIndex(0);
        EasyJdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getEasyJdbcTypeHandlerManager();
        JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(clazz);
        return handler.getValue(easyResultSet);

    }

    private <T> List<T> mapToBeans(ExecutorContext context, ResultSet rs, Class<T> clazz) throws SQLException {
        if (!rs.next()) {
            return new ArrayList<>(0);
        }
        List<T> resultList = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        PropertyDescriptor[] propertyDescriptors = columnsToProperties(context, rs, rsmd, clazz);
        do {
            T bean = mapToBean(context, rs, clazz, propertyDescriptors);
            resultList.add(bean);
        } while (rs.next());
        return resultList;
    }

    private boolean trackBean(ExecutorContext context, Class<?> clazz){
        if(context.isTracking()){
            EasyQueryRuntimeContext runtimeContext = context.getRuntimeContext();
            TrackManager trackManager = runtimeContext.getTrackManager();
            //当前是否开启追踪
            if(!trackManager.currentThreadTracking()){
                return false;
            }
            //如果当前返回结果不是数据库实体就直接选择不追踪
            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
            if(StringUtil.isBlank(entityMetadata.getTableName())){
                return false;
            }
            return true;
        }
        return false;
    }
    private <T> T mapToBean(ExecutorContext context, ResultSet rs, Class<T> clazz, PropertyDescriptor[] propertyDescriptors) throws SQLException {
        EasyJdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getEasyJdbcTypeHandlerManager();
        TrackManager trackManager = context.getRuntimeContext().getTrackManager();
        boolean trackBean = trackBean(context, clazz);
        EasyResultSet easyResultSet = new EasyResultSet(rs);
        T bean = ClassUtil.newInstance(clazz);

        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
            if (propertyDescriptor == null) {
                continue;
            }
            easyResultSet.setIndex(i);
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            easyResultSet.setPropertyType(propertyType);
            JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(propertyType);
            Object value = handler.getValue(easyResultSet);
            Method setter = getSetter(propertyDescriptor, clazz);
            callSetter(bean,setter, propertyDescriptor, value);
        }
        if(trackBean){
            trackManager.getCurrentTrackContext().addTracking(bean,true);
        }
        return bean;
    }

    public Method getSetter(PropertyDescriptor prop, Class<?> targetClass){
        return ClassUtil.getWriteMethod(prop, targetClass);
    }
    public void callSetter(Object target,Method setter, PropertyDescriptor prop, Object value) throws SQLException {
        try {
            setter.invoke(target, value);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            throw new SQLException("Cannot set " + prop.getName() + ",value: " + value + ".: " + e.getMessage(), e);
        }

    }

    protected String getColName(ResultSetMetaData rsmd, int col) throws SQLException {
        String columnName = rsmd.getColumnLabel(col);
        if (StringUtil.isEmpty(columnName)) {
            columnName = rsmd.getColumnName(col);
        }
        return columnName;
    }
}
