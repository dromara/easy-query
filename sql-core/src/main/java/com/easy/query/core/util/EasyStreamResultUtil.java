package com.easy.query.core.util;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;
import com.easy.query.core.basic.jdbc.types.JdbcTypes;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.BeanMapToColumnMetadata;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.beans.PropertyDescriptor;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/4/16 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyStreamResultUtil {

    private static final Log log= LogFactory.getLog(EasyStreamResultUtil.class);
    private EasyStreamResultUtil(){}

    public static  <TResult> List<TResult> mapTo(ExecutorContext context, StreamResultSet streamResult, Class<TResult> clazz) throws SQLException{
        List<TResult> resultList = null;
        if (Map.class.isAssignableFrom(clazz)) {
            resultList = mapToMaps(context, streamResult, clazz);
        } else if (EasyClassUtil.isBasicType(clazz)) {//如果返回的是基本类型
            resultList = new ArrayList<>();
            while (streamResult.next()) {
                Object value = mapToBasic(context, streamResult, clazz);
                resultList.add((TResult) value);
            }
        } else {
            resultList = mapToBeans(context, streamResult, clazz);
        }
        if(log.isDebugEnabled()){
            log.debug("<== "+"Total: " + resultList.size());
        }
        return resultList;
    }
    private static <T> List<T> mapToMaps(ExecutorContext context, StreamResultSet streamResult, Class<T> clazz) throws SQLException {

        if (!streamResult.next()) {
            return new ArrayList<>(0);
        }
        List<T> resultList = new ArrayList<>();
        ResultSetMetaData rsmd = streamResult.getMetaData();
        do {
            Map bean = mapToMap(context, streamResult, clazz, rsmd);
            resultList.add((T) bean);
        } while (streamResult.next());
        return resultList;
    }

    private static Map<String, Object> mapToMap(ExecutorContext context, StreamResultSet streamResult, Class<?> clazz, ResultSetMetaData rsmd) throws SQLException {
        Map<String, Object> map = EasyClassUtil.newMapInstanceOrNull(clazz);
        if (map == null) {
            throw new SQLException("cant create map:" + EasyClassUtil.getSimpleName(clazz));
        }
        JdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getJdbcTypeHandlerManager();
        int columnCount = rsmd.getColumnCount();//有多少列
        EasyResultSet easyResultSet = new EasyResultSet(streamResult);
        for (int i = 0; i < columnCount; i++) {

            String colName = getColName(rsmd, i + 1);//数据库查询出来的列名
            easyResultSet.setIndex(i);
            int columnType = rsmd.getColumnType(i + 1);
            Class<?> propertyType = JdbcTypes.jdbcJavaTypes.get(columnType);
            easyResultSet.setPropertyType(propertyType);
            JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(propertyType);
            Object value = handler.getValue(easyResultSet);
            Object o = map.put(colName, value);
            if(o!=null){
                throw new IllegalStateException("Duplicate key found: " + colName);
            }
        }
        return map;
    }

    private static  <T> Object mapToBasic(ExecutorContext context, StreamResultSet streamResult, Class<T> clazz) throws SQLException {
        ResultSetMetaData rsmd = streamResult.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (columnCount != 1) {
            throw new SQLException("返回类型:" + clazz + ",期望返回一列");
        }
        EasyResultSet easyResultSet = new EasyResultSet(streamResult);
        easyResultSet.setIndex(0);
        JdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getJdbcTypeHandlerManager();
        JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(clazz);
        return handler.getValue(easyResultSet);

    }
    private static  <TResult> List<TResult> mapToBeans(ExecutorContext context, StreamResultSet streamResult, Class<TResult> clazz) throws SQLException {
        if (!streamResult.next()) {
            return new ArrayList<>(0);
        }
        List<TResult> resultList = new ArrayList<>();
        ResultSetMetaData rsmd = streamResult.getMetaData();
        BeanMapToColumnMetadata[] propertyDescriptors = columnsToProperties(context,rsmd, clazz);
        do {
            TResult bean = mapToBean(context, streamResult, clazz, propertyDescriptors);
            resultList.add(bean);
        } while (streamResult.next());
        return resultList;
    }
    private static  <TResult> TResult mapToBean(ExecutorContext context, StreamResultSet streamResult, Class<TResult> clazz, BeanMapToColumnMetadata[] beanMapToColumnMetadatas) throws SQLException {

        TrackManager trackManager = context.getRuntimeContext().getTrackManager();
        boolean trackBean = trackBean(context, clazz);
        EasyResultSet easyResultSet = new EasyResultSet(streamResult);
        TResult bean = EasyClassUtil.newInstance(clazz);

        FastBean beanFastSetter = EasyBeanUtil.getFastBean(clazz);
        for (int i = 0; i < beanMapToColumnMetadatas.length; i++) {
            BeanMapToColumnMetadata beanMapToColumnMetadata = beanMapToColumnMetadatas[i];
            if (beanMapToColumnMetadata == null) {
                continue;
            }
            easyResultSet.setIndex(i);
            ColumnMetadata columnMetadata = beanMapToColumnMetadata.getColumnMetadata();
            JdbcTypeHandler handler = beanMapToColumnMetadata.getJdbcTypeHandler();
            Class<?> propertyType = columnMetadata.getPropertyType();
            easyResultSet.setPropertyType(propertyType);
            Object value = context.fromValue(clazz,columnMetadata, handler.getValue(easyResultSet));

            //可能存在value为null但是bean默认有初始值,所以必须还是要调用set方法将其设置为null而不是默认值
            PropertyDescriptor property = columnMetadata.getProperty();
            PropertySetterCaller<Object> beanSetter = beanFastSetter.getBeanSetter(property);
            beanSetter.call(bean, value);
//            Method setter = getSetter(property, clazz);
//            callSetter(bean,setter, property, value);
        }
        if (trackBean) {
            trackManager.getCurrentTrackContext().addTracking(bean, true);
        }
        return bean;
    }

    private static boolean trackBean(ExecutorContext context, Class<?> clazz) {
        //当前查询是否使用了追踪如果没有就直接不使用追踪
        if (context.isTracking()) {
            QueryRuntimeContext runtimeContext = context.getRuntimeContext();
            TrackManager trackManager = runtimeContext.getTrackManager();
            //当前是否开启追踪
            if (!trackManager.currentThreadTracking()) {
                return false;
            }
            //如果当前返回结果不是数据库实体就直接选择不追踪
            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
            if (EasyStringUtil.isBlank(entityMetadata.getTableName())) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static  <TResult> BeanMapToColumnMetadata[] columnsToProperties(ExecutorContext context,ResultSetMetaData rsmd, Class<TResult> clazz) throws SQLException {
        QueryRuntimeContext runtimeContext = context.getRuntimeContext();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        JdbcTypeHandlerManager easyJdbcTypeHandler = runtimeContext.getJdbcTypeHandlerManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(clazz);
        //需要返回的结果集映射到bean实体上
        //int[] 索引代表数据库返回的索引，数组索引所在的值代表属性数组的对应属性
        int columnCount = rsmd.getColumnCount();//有多少列
        BeanMapToColumnMetadata[] beanMapToColumnMetadatas = new BeanMapToColumnMetadata[columnCount];
        for (int i = 0; i < columnCount; i++) {

            String colName = getColName(rsmd, i + 1);//数据库查询出来的列名

            String propertyName = entityMetadata.getPropertyNameOrNull(colName);
            if (propertyName == null) {
                continue;
            }
            ColumnMetadata column = entityMetadata.getColumnOrNull(propertyName);
            Class<?> propertyType = column.getPropertyType();
            JdbcTypeHandler jdbcTypeHandler = easyJdbcTypeHandler.getHandler(propertyType);
            BeanMapToColumnMetadata beanMapToColumnMetadata = new BeanMapToColumnMetadata(column, jdbcTypeHandler);
            beanMapToColumnMetadatas[i] = beanMapToColumnMetadata;
        }
        return beanMapToColumnMetadatas;
    }
    private static String getColName(ResultSetMetaData rsmd, int col) throws SQLException {
        String columnName = rsmd.getColumnLabel(col);
        if (EasyStringUtil.isEmpty(columnName)) {
            columnName = rsmd.getColumnName(col);
        }
        return columnName;
    }

}
