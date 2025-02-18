package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.RelationExtraResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.reader.BeanDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.EmptyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.PartitionByPropertyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.PropertyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.RelationExtraPropertyDataReader;
import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.RelationExtraColumn;
import com.easy.query.core.metadata.RelationExtraMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * create time 2023/7/31 16:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultBeanStreamIterator<T> extends AbstractMapToStreamIterator<T> {
    private static final Log log = LogFactory.getLog(DefaultBeanStreamIterator.class);
    protected boolean trackBean;
    protected TrackManager trackManager;
    protected DataReader dataReader;
    protected RelationExtraMetadata relationExtraMetadata;

    public DefaultBeanStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);

    }

    @Override
    protected void init0() throws SQLException {
        ResultSetMetaData rsmd = streamResultSet.getMetaData();
        if(context.getExpressionContext().hasRelationExtraMetadata()){
            relationExtraMetadata =context.getExpressionContext().getRelationExtraMetadata();
        }
        this.dataReader = getColumnDataReader(rsmd);
        this.trackBean = EasyTrackUtil.trackBean(context, resultMetadata.getResultClass());
        this.trackManager = trackBean ? context.getRuntimeContext().getTrackManager() : null;
    }

    @Override
    protected T next0() throws SQLException {
        T bean = mapTo();
        if(hasForEach){
            context.getExpressionContext().getForEachConfigurer().accept(bean);
        }
        if (trackBean && bean != null) {
            EntityState entityState = trackManager.getCurrentTrackContext().addQueryTracking(bean);
            Object entityStateCurrentValue = entityState.getCurrentValue();
            if (entityStateCurrentValue != bean) {//没有附加成功应该返回之前被追加的数据而不是最新查询的数据
                log.warn("current object tracked,return the traced object instead of the current querying object,track key:" + entityState.getTrackKey());
                return EasyObjectUtil.typeCastNullable(entityStateCurrentValue);
            }
        }
        return bean;
    }

    /**
     * 映射到bean
     * @return
     * @throws SQLException
     */
    @Override
    protected T mapTo() throws SQLException {
        T bean = resultMetadata.newBean();
        if(relationExtraMetadata!=null){
            this.relationExtraMetadata.createRow();
        }
        dataReader.readTo(bean, streamResultSet);
        //todo forEach
        return bean;
    }

    private DataReader getColumnDataReader(ResultSetMetaData rsmd) throws SQLException {
        boolean mapToBeanStrict = context.isMapToBeanStrict();

        //需要返回的结果集映射到bean实体上
        //int[] 索引代表数据库返回的索引，数组索引所在的值代表属性数组的对应属性
        int columnCount = rsmd.getColumnCount();//有多少列
        DataReader dataReader = EmptyDataReader.EMPTY;
        for (int i = 0; i < columnCount; i++) {
            String colName = getColName(rsmd, i + 1);//数据库查询出来的列名
            if (KeywordTool.isIgnoreColumn(colName)) {
                continue;
            }
            ResultColumnMetadata resultColumnMetadata = getMapColumnMetadata(i, colName, mapToBeanStrict);
            if (resultColumnMetadata == null) {
                if(relationExtraMetadata!=null){
                    Map<String, RelationExtraColumn> relationExtraColumnMap = relationExtraMetadata.getRelationExtraColumnMap();
                    RelationExtraColumn relationExtraColumn = relationExtraColumnMap.get(colName);
                    if(relationExtraColumn!=null){
                        RelationExtraResultColumnMetadata relationExtraResultColumnMetadata = new RelationExtraResultColumnMetadata(i, relationExtraMetadata, relationExtraColumn);
                        dataReader = new BeanDataReader(dataReader, new RelationExtraPropertyDataReader(relationExtraResultColumnMetadata));
                        continue;
                    } else if(easyQueryOption.isWarningColumnMiss()){
                        log.warn("!!!sql result column name:["+colName+"] mapping miss in class:["+ EasyClassUtil.getSimpleName(resultMetadata.getResultClass()) +"]");
                    }
                } else if(easyQueryOption.isWarningColumnMiss()){
                    log.warn("!!!sql result column name:["+colName+"] mapping miss in class:["+ EasyClassUtil.getSimpleName(resultMetadata.getResultClass()) +"]");
                }
                continue;
            }
            if(PartitionResult.class.isAssignableFrom(resultMetadata.getResultClass())){
                dataReader = new BeanDataReader(dataReader, new PartitionByPropertyDataReader(resultColumnMetadata));
            }else{
                dataReader = new BeanDataReader(dataReader, new PropertyDataReader(resultColumnMetadata));
            }
        }
        return dataReader;
    }


    private ResultColumnMetadata getMapColumnMetadata(int index, String columnName, boolean mapToBeanStrict) {
        ResultColumnMetadata resultColumnMetadata = resultMetadata.getResultColumnOrNullByColumnName(index, columnName);
        if (resultColumnMetadata != null) {
            return resultColumnMetadata;
        } else if (!mapToBeanStrict) {
            return resultMetadata.getResultColumnOrNullByPropertyName(index, columnName);
        }
        return null;
    }
}
