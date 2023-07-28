package com.easy.query.core.util;

import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.JdbcTypes;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;

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

    private static final Log log = LogFactory.getLog(EasyStreamResultUtil.class);

    private EasyStreamResultUtil() {
    }

    public static <TResult> List<TResult> mapTo(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<TResult> resultMetadata) throws SQLException {
        List<TResult> resultList = null;
        switch (resultMetadata.getEntityMetadataType()) {
            case MAP:
                resultList = mapToMaps(context, streamResult, resultMetadata);
                break;
            case BASIC_TYPE: {
                resultList = new ArrayList<>();
                while (streamResult.next()) {
                    Object value = mapToBasic(context, streamResult, resultMetadata);
                    resultList.add((TResult) value);
                }
            }
            break;
            default:
                resultList = mapToBeans(context, streamResult, resultMetadata);
                break;
        }
        boolean printSql = context.getEasyQueryOption().isPrintSql();
        if (printSql) {
            log.info("<== " + "Total: " + resultList.size());
        }
        return resultList;
    }

    private static <T> List<T> mapToMaps(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {

        if (!streamResult.next()) {
            return new ArrayList<>(0);
        }
        List<T> resultList = new ArrayList<>();
        ResultSetMetaData rsmd = streamResult.getMetaData();
        do {
            Map bean = mapToMap(context, streamResult, resultMetadata.getResultClass(), rsmd);
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
            if (o != null) {
                throw new IllegalStateException("Duplicate key found: " + colName);
            }
        }
        return map;
    }

    private static <T> Object mapToBasic(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {
        ResultSetMetaData rsmd = streamResult.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (columnCount != 1) {
            throw new SQLException("result set column count:" + EasyClassUtil.getSimpleName(resultMetadata.getResultClass()) + ",expect one column");
        }
        EasyResultSet easyResultSet = new EasyResultSet(streamResult);
        easyResultSet.setIndex(0);
        JdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getJdbcTypeHandlerManager();
        JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(resultMetadata.getResultClass());
        return handler.getValue(easyResultSet);

    }

    private static <TResult> List<TResult> mapToBeans(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<TResult> resultMetadata) throws SQLException {
        if (!streamResult.next()) {
            return new ArrayList<>(0);
        }
        List<TResult> resultList = new ArrayList<>();
        ResultSetMetaData rsmd = streamResult.getMetaData();
        ResultColumnMetadata[] columnMetadatas = columnsToColumnMetadatas(context, resultMetadata, rsmd);

        boolean trackBean = EasyTrackUtil.trackBean(context, resultMetadata.getResultClass());
        TrackManager trackManager = trackBean ? context.getRuntimeContext().getTrackManager() : null;

        EasyResultSet easyResultSet = new EasyResultSet(streamResult);
        do {
            TResult bean = mapToBean(context, easyResultSet, resultMetadata, columnMetadatas);
            resultList.add(bean);
            if (trackBean) {
                EntityState entityState = trackManager.getCurrentTrackContext().addQueryTracking(bean);
                Object entityStateCurrentValue = entityState.getCurrentValue();
                if (entityStateCurrentValue != bean) {//没有附加成功应该返回之前被追加的数据而不是最新查询的数据
                    log.warn("current object tracked,return the traced object instead of the current querying object,track key:" + entityState.getTrackKey());
                    return EasyObjectUtil.typeCastNullable(entityStateCurrentValue);
                }
            }
        } while (easyResultSet.nextAndReset());
        return resultList;
    }

    private static <TResult> TResult mapToBean(ExecutorContext context, EasyResultSet easyResultSet, ResultMetadata<TResult> resultMetadata, ResultColumnMetadata[] columnMetadatas) throws SQLException {

        TResult bean = resultMetadata.newBean();
        Class<?> entityClass = resultMetadata.getResultClass();
        for (int i = 0; i < columnMetadatas.length; i++) {
            ResultColumnMetadata resultColumnMetadata = columnMetadatas[i];
            if (resultColumnMetadata == null) {
                continue;
            }
            easyResultSet.setIndex(i);
            JdbcTypeHandler handler = resultColumnMetadata.getJdbcTypeHandler();
            Class<?> propertyType = resultColumnMetadata.getPropertyType();
            easyResultSet.setPropertyType(propertyType);
            Object value = context.fromValue(entityClass, resultColumnMetadata, handler.getValue(easyResultSet));

            resultColumnMetadata.setValue(bean,value);
//            //可能存在value为null但是bean默认有初始值,所以必须还是要调用set方法将其设置为null而不是默认值
//            PropertySetterCaller<Object> beanSetter = columnMetadata.getSetterCaller();
//            beanSetter.call(bean, value);
        }
        return bean;
    }

    public static <TResult> ResultColumnMetadata[] columnsToColumnMetadatas(ExecutorContext context, ResultMetadata<TResult> resultMetadata, ResultSetMetaData rsmd) throws SQLException {
        boolean mapToBeanStrict = context.isMapToBeanStrict();
        //需要返回的结果集映射到bean实体上
        //int[] 索引代表数据库返回的索引，数组索引所在的值代表属性数组的对应属性
        int columnCount = rsmd.getColumnCount();//有多少列
        ResultColumnMetadata[] columnMetadatas = new ResultColumnMetadata[columnCount];
        for (int i = 0; i < columnCount; i++) {
            String colName = getColName(rsmd, i + 1);//数据库查询出来的列名
            if(KeywordTool.isIgnoreColumn(colName)){
                continue;
            }
            ResultColumnMetadata column = getMapColumnMetadata(resultMetadata, colName, mapToBeanStrict);
            if (column == null) {
                continue;
            }
            columnMetadatas[i] = column;
        }
        return columnMetadatas;
    }

    private static <TResult> ResultColumnMetadata getMapColumnMetadata(ResultMetadata<TResult> resultMetadata, String columnName, boolean mapToBeanStrict) {
        ResultColumnMetadata resultColumnMetadata = resultMetadata.getResultColumnOrNullByColumnName(columnName);
        if (resultColumnMetadata != null) {
            return resultColumnMetadata;
        } else if (!mapToBeanStrict) {
            return resultMetadata.getResultColumnOrNullByPropertyName(columnName);
        }
        return null;
    }

    private static String getColName(ResultSetMetaData rsmd, int col) throws SQLException {
        String columnName = rsmd.getColumnLabel(col);
        if (EasyStringUtil.isEmpty(columnName)) {
            columnName = rsmd.getColumnName(col);
        }
        return columnName;
    }

}
