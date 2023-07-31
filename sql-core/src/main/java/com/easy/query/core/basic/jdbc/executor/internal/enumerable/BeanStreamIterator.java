package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * create time 2023/7/31 16:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class BeanStreamIterator<T> extends AbstractStreamIterator<T> {
    private static final Log log = LogFactory.getLog(BeanStreamIterator.class);
    private ResultColumnMetadata[] resultColumnMetadatas;
    private boolean trackBean;
    private TrackManager trackManager;

    public BeanStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {
        super(context,streamResult,resultMetadata);
    }

    @Override
    protected void init0() throws SQLException {
        ResultSetMetaData rsmd = streamResultSet.getMetaData();
        this.resultColumnMetadatas = columnsToColumnMetadatas(rsmd);
        this.trackBean = EasyTrackUtil.trackBean(context, resultMetadata.getResultClass());
        this.trackManager = trackBean ? context.getRuntimeContext().getTrackManager() : null;
    }



    protected T next0() throws SQLException {
        T bean = mapToBean();
        if (trackBean) {
            EntityState entityState = trackManager.getCurrentTrackContext().addQueryTracking(bean);
            Object entityStateCurrentValue = entityState.getCurrentValue();
            if (entityStateCurrentValue != bean) {//没有附加成功应该返回之前被追加的数据而不是最新查询的数据
                log.warn("current object tracked,return the traced object instead of the current querying object,track key:" + entityState.getTrackKey());
                return EasyObjectUtil.typeCastNullable(entityStateCurrentValue);
            }
        }
        return bean;
    }
    private  T mapToBean() throws SQLException {
        T bean = resultMetadata.newBean();
        Class<?> entityClass = resultMetadata.getResultClass();
        for (int i = 0; i < this.resultColumnMetadatas.length; i++) {
            ResultColumnMetadata resultColumnMetadata = this.resultColumnMetadatas[i];
            if (resultColumnMetadata == null) {
                continue;
            }
            easyResultSet.setIndex(i);
            JdbcTypeHandler handler = resultColumnMetadata.getJdbcTypeHandler();
            Class<?> propertyType = resultColumnMetadata.getPropertyType();
            easyResultSet.setPropertyType(propertyType);
            Object value = context.fromValue(entityClass, resultColumnMetadata, handler.getValue(easyResultSet));

            resultColumnMetadata.setValue(bean,value);
        }
        return bean;
    }

    private ResultColumnMetadata[] columnsToColumnMetadatas(ResultSetMetaData rsmd) throws SQLException {
        boolean mapToBeanStrict = context.isMapToBeanStrict();
        //需要返回的结果集映射到bean实体上
        //int[] 索引代表数据库返回的索引，数组索引所在的值代表属性数组的对应属性
        int columnCount = rsmd.getColumnCount();//有多少列
        ResultColumnMetadata[] columnMetadatas = new ResultColumnMetadata[columnCount];
        for (int i = 0; i < columnCount; i++) {
            String colName = getColName(rsmd, i + 1);//数据库查询出来的列名
            if (KeywordTool.isIgnoreColumn(colName)) {
                continue;
            }
            ResultColumnMetadata column = getMapColumnMetadata(colName, mapToBeanStrict);
            if (column == null) {
                continue;
            }
            columnMetadatas[i] = column;
        }
        return columnMetadatas;
    }


    private ResultColumnMetadata getMapColumnMetadata(String columnName, boolean mapToBeanStrict) {
        ResultColumnMetadata resultColumnMetadata = resultMetadata.getResultColumnOrNullByColumnName(columnName);
        if (resultColumnMetadata != null) {
            return resultColumnMetadata;
        } else if (!mapToBeanStrict) {
            return resultMetadata.getResultColumnOrNullByPropertyName(columnName);
        }
        return null;
    }
}
