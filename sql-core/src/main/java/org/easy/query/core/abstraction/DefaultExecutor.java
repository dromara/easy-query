package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
import org.easy.query.core.basic.EasyConnection;
import org.easy.query.core.basic.EasyConnectionManager;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;
import org.easy.query.core.executor.type.JdbcTypeHandler;
import org.easy.query.core.util.ClassUtil;
import org.easy.query.core.util.StringUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @FileName: DefaultExecutor.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:49
 * @Created by xuejiaming
 */
public class DefaultExecutor implements EasyExecutor {
    private static final int PROPERTY_NOT_FOUND = -1;

    @Override
    public <TR> List<TR> execute(ExecutorContext executorContext, Class<TR> clazz, String sql, List<Object> parameters) {
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        EasyConnectionManager connectionManager = runtimeContext.getConnectionManager();
        EasyJdbcTypeHandler easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandler();
        List<TR> result = null;
        System.out.println("开始执行：" + sql);
        EasyConnection easyConnection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            easyConnection = connectionManager.getEasyConnection();
            ps = createPreparedStatement(easyConnection.getConnection(), sql, parameters, easyJdbcTypeHandler);
            rs = ps.executeQuery();
            result = mapTo(executorContext, rs, clazz);
        } catch (SQLException e) {
            throw new JDQCException(e);
        } finally {
            clean(executorContext,easyConnection,rs,ps);
        }
        return result;
    }

    protected void clean(ExecutorContext executorContext, EasyConnection easyConnection, ResultSet rs, PreparedStatement ps) {
        EasyConnectionManager connectionManager = executorContext.getRuntimeContext().getConnectionManager();
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            connectionManager.closeEasyCConnection(easyConnection);
        } catch (SQLException ignored) {
        }
    }

    private PreparedStatement createPreparedStatement(Connection connection, String sql, List<Object> parameters, EasyJdbcTypeHandler easyJdbcTypeHandler) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        EasyParameter easyParameter = new EasyParameter(preparedStatement, parameters);
        int paramSize = parameters.size();
        for (int i = 0; i < paramSize; i++) {
            easyParameter.setIndex(i);
            JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(easyParameter.getValue().getClass());
            handler.setParameter(easyParameter);
        }
        return preparedStatement;
    }

    private <T> List<T> mapTo(ExecutorContext context, ResultSet rs, Class<T> clazz) throws SQLException {
        List<T> resultList = null;
        if (Map.class.isAssignableFrom(clazz)) {
            throw new JDQCException("不支持：" + clazz.getSimpleName() + "的转换");
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
            ColumnMetadata column = entityMetadata.getColumn(propertyName);
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
        EasyJdbcTypeHandler easyJdbcTypeHandler = context.getRuntimeContext().getEasyJdbcTypeHandler();
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

    private <T> T mapToBean(ExecutorContext context, ResultSet rs, Class<T> clazz, PropertyDescriptor[] propertyDescriptors) throws SQLException {
        EasyJdbcTypeHandler easyJdbcTypeHandler = context.getRuntimeContext().getEasyJdbcTypeHandler();
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
            callSetter(bean, propertyDescriptor, value);
        }
        return bean;
    }

    public void callSetter(Object target, PropertyDescriptor prop, Object value) throws SQLException {

        Method setter = ClassUtil.getWriteMethod(prop, target.getClass());
        if (setter == null) {
            return;
        }
        try {
            setter.invoke(target, value);
        } catch (IllegalArgumentException e) {
            throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
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
