package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.extension.conversion.ColumnReader;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.BasicResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyJdbcExecutorUtil;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * create time 2023/7/31 17:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class DraftStreamIterator extends AbstractMapToStreamIterator<DraftResult> {
    private ResultSetMetaData rsmd;
    private ResultColumnMetadata[] resultBasicMetadatas;
    private int mapCount = -1;
    protected Map<String, ColumnReader> resultValueConverterMap;

    public DraftStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<DraftResult> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);
    }

    @Override
    protected void init0() throws SQLException {
        rsmd = streamResultSet.getMetaData();
        this.resultValueConverterMap = context.getExpressionContext().getResultValueConverterMap(false);
        int columnCount = rsmd.getColumnCount();//有多少列
        resultBasicMetadatas = new ResultColumnMetadata[columnCount];
    }

    private @Nullable ResultColumnMetadata getDraftPropType(int index) {
        ResultColumnMetadata[] draftPropTypes = context.getExpressionContext().getResultPropTypes();
        if (draftPropTypes != null) {
            return draftPropTypes[index];
        }
        return null;
    }

    @Override
    protected DraftResult mapTo() throws SQLException {

        mapCount++;
        Class<DraftResult> clazz = resultMetadata.getResultClass();
        DraftResult draft = EasyClassUtil.newInstance(clazz);
        JdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getJdbcTypeHandlerManager();

        if (mapCount == 0) {
            resultBasicMetadatas = new ResultColumnMetadata[draft.capacity()];
        }
        for (int i = 0; i < resultBasicMetadatas.length; i++) {
            if (!draft.readColumn(i)) {
                break;
            }

            if (mapCount == 0) {
                ResultColumnMetadata propType = getDraftPropType(i);
                if (propType != null) {
                    if (propType instanceof EntityResultColumnMetadata) {
                        resultBasicMetadatas[i] = propType;
                    } else {
                        resultBasicMetadatas[i] = new BasicResultColumnMetadata(propType.getPropertyType(), easyJdbcTypeHandler.getHandler(propType.getPropertyType()), propType.getJdbcProperty());
                    }
                }
            }
            ResultColumnMetadata resultColumnMetadata = resultBasicMetadatas[i];
            if (resultColumnMetadata == null) {
                draft.setValues(i, streamResultSet.getObject(i + 1));
            } else {

                Object value = getValue(i, resultColumnMetadata, streamResultSet);
                draft.setValues(i, value);
            }
        }
        return draft;
    }

    private Object getValue(int i, ResultColumnMetadata resultColumnMetadata, StreamResultSet streamResultSet) throws SQLException {
        ColumnReader columnReader = getValueConverterColumnReader(i);
        if (columnReader != null) {
            return columnReader.read(resultColumnMetadata.getJdbcProperty().getJdbcIndex() - 1, streamResultSet);
        } else {
            if (resultColumnMetadata instanceof EntityResultColumnMetadata) {
                JdbcTypeHandler handler = resultColumnMetadata.getJdbcTypeHandler();
                JdbcProperty jdbcProperty = resultColumnMetadata.getJdbcProperty();
                return EasyJdbcExecutorUtil.fromValue(resultColumnMetadata, handler.getValue(jdbcProperty, streamResultSet));
            } else {
                return resultColumnMetadata.getJdbcTypeHandler().getValue(resultColumnMetadata.getJdbcProperty(), streamResultSet);
            }
        }

    }

    private ColumnReader getValueConverterColumnReader(int columnIndex) {
        if (resultValueConverterMap != null) {
            return resultValueConverterMap.get("value" + (columnIndex + 1));
        }
        return null;
    }
}
