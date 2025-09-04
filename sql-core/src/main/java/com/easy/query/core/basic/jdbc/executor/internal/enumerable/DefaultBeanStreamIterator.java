package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.extension.conversion.ColumnReader;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.DeepResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.RelationExtraResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.BasicJdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.reader.BeanDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.DeepColumnDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.EmptyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.PartByPropertyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.PropertyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.RelationExtraPropertyDataReader;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEOption;
import com.easy.query.core.expression.sql.include.ColumnIncludeExpression;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.RelationExtraColumn;
import com.easy.query.core.metadata.RelationExtraMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    protected Map<String, ColumnReader> resultValueConverterMap;
    protected List<NavigateMetadata> includes;

    public DefaultBeanStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);
        if (trackBean) {
            Map<NavigateMetadata, IncludeNavigateExpression> includes = context.getExpressionContext().getIncludes();
            this.includes = new ArrayList<>(includes.keySet());
        }else{
            this.includes = null;
        }
    }

    @Override
    protected void init0() throws SQLException {
        ResultSetMetaData rsmd = streamResultSet.getMetaData();
        if (context.getExpressionContext().hasRelationExtraMetadata()) {
            relationExtraMetadata = context.getExpressionContext().getRelationExtraMetadata();
            relationExtraMetadata.clearRow();
        }
        this.resultValueConverterMap = context.getExpressionContext().getResultValueConverterMap(false);
        this.dataReader = getColumnDataReader(rsmd);
        this.trackBean = EasyTrackUtil.trackBean(context, resultMetadata.getResultClass());
        this.trackManager = trackBean ? context.getRuntimeContext().getTrackManager() : null;
    }

    @Override
    protected T next0() throws SQLException {
        T bean = mapTo();
        if (hasForEach) {
            context.getExpressionContext().getForEachConfigurer().accept(bean);
        }
        if (trackBean && bean != null) {
            EntityState entityState = trackManager.getCurrentTrackContext().addQueryTracking(bean);
            entityState.setIncludes(includes);
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
     *
     * @return
     * @throws SQLException
     */
    @Override
    protected T mapTo() throws SQLException {
        T bean = resultMetadata.newBean();
        if (relationExtraMetadata != null) {
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
                DataReader reader = appendBeanDataReader(i, colName, dataReader);
                if (reader != null) {
                    dataReader = reader;
                }
                continue;
            }
            if (PartResult.class.isAssignableFrom(resultMetadata.getResultClass())) {
                dataReader = new BeanDataReader(dataReader, new PartByPropertyDataReader(resultColumnMetadata));
            } else {
                dataReader = new BeanDataReader(dataReader, new PropertyDataReader(resultColumnMetadata, getValueConverterColumnReader(resultColumnMetadata)));
            }
        }
        return dataReader;
    }

    private DataReader appendBeanDataReader(int i, String colName, DataReader dataReader) {
        TreeCTEOption treeCTEOption = context.getExpressionContext().getTreeCTEOption();
        boolean isDeepColumn = treeCTEOption != null && Objects.equals(treeCTEOption.getDeepColumnName(), colName);
        if (relationExtraMetadata != null) {
            Map<String, RelationExtraColumn> relationExtraColumnMap = relationExtraMetadata.getRelationExtraColumnMap();
            RelationExtraColumn relationExtraColumn = relationExtraColumnMap.get(colName);
            if (relationExtraColumn != null) {
                RelationExtraResultColumnMetadata relationExtraResultColumnMetadata = new RelationExtraResultColumnMetadata(i, relationExtraMetadata, relationExtraColumn);
                return new BeanDataReader(dataReader, new RelationExtraPropertyDataReader(relationExtraResultColumnMetadata));
            }
        }
        if (isDeepColumn) {
            BasicJdbcProperty basicJdbcProperty = new BasicJdbcProperty(i, Long.class);
            JdbcTypeHandler jdbcTypeHandler = context.getRuntimeContext().getJdbcTypeHandlerManager().getHandler(long.class);
            DeepResultColumnMetadata deepResultColumnMetadata = new DeepResultColumnMetadata(context.getExpressionContext().getDeepItems(), jdbcTypeHandler, basicJdbcProperty);
            return new BeanDataReader(dataReader, new DeepColumnDataReader(deepResultColumnMetadata));
        } else {
            if (easyQueryOption.isWarningColumnMiss()) {
                log.warn("!!!sql result column name:[" + colName + "] mapping miss in class:[" + EasyClassUtil.getSimpleName(resultMetadata.getResultClass()) + "]");
            }
        }

        return null;

    }

    private ColumnReader getValueConverterColumnReader(ResultColumnMetadata resultColumnMetadata) {
        if (resultValueConverterMap != null) {
            return resultValueConverterMap.get(resultColumnMetadata.getPropertyName());
        }
        return null;
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
