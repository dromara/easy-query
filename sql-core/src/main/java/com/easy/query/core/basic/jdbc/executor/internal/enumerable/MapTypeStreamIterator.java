package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultBasicMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.TypeResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.BasicJdbcProperty;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create time 2023/7/31 17:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapTypeStreamIterator extends AbstractMapToStreamIterator<Map<String, Object>> {
    private ResultSetMetaData rsmd;
    private ResultBasicMetadata[] resultBasicMetadatas;
    private Map<String, ResultColumnMetadata> resultColumnMetadataMap;
    private ResultColumnMetadata[] typeIndexColumnMetadataArray;
    private int mapCount = -1;

    public MapTypeStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<Map<String, Object>> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);
    }

    @Override
    protected void init0() throws SQLException {
        rsmd = streamResultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();//有多少列
        resultBasicMetadatas = new ResultBasicMetadata[columnCount];
        ResultColumnMetadata[] resultPropTypes = context.getExpressionContext().getResultPropTypes();
        this.typeIndexColumnMetadataArray = new ResultColumnMetadata[columnCount];
        this.resultColumnMetadataMap = new HashMap<>();
        for (ResultColumnMetadata resultPropType : resultPropTypes) {
            if (resultPropType instanceof TypeResultColumnMetadata) {
                TypeResultColumnMetadata typeResultColumnMetadata = (TypeResultColumnMetadata) resultPropType;
                resultColumnMetadataMap.put(typeResultColumnMetadata.getColName(), typeResultColumnMetadata);
            }
        }
    }

    private Class<?> getPropTypeOrObjectType(String colName, int index) {
        ResultColumnMetadata typeIndexColumnMetadata = typeIndexColumnMetadataArray[index];
        if (typeIndexColumnMetadata != null) {
            return typeIndexColumnMetadata.getPropertyType();
        }
        ResultColumnMetadata resultColumnMetadata = resultColumnMetadataMap.get(colName);
        if (resultColumnMetadata != null) {
            typeIndexColumnMetadataArray[index] = resultColumnMetadata;
            return resultColumnMetadata.getPropertyType();
        }

        return Object.class;
    }

    @Override
    protected Map<String, Object> mapTo() throws SQLException {
        mapCount++;
        int length = resultBasicMetadatas.length;
        Map<String, Object> map = new LinkedHashMap<String, Object>(length);
        JdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getJdbcTypeHandlerManager();

        if (mapCount == 0) {
            resultBasicMetadatas = new ResultBasicMetadata[length];
        }
        for (int i = 0; i < resultBasicMetadatas.length; i++) {

            if (mapCount == 0) {
                String colName = getColName(rsmd, i + 1);//数据库查询出来的列名
                Class<?> propType = getPropTypeOrObjectType(colName, i);
                JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(propType);
                BasicJdbcProperty dataReader = new BasicJdbcProperty(i, propType);
                resultBasicMetadatas[i] = new ResultBasicMetadata(colName, dataReader, handler);
            }
            ResultBasicMetadata resultBasicMetadata = resultBasicMetadatas[i];

            Object value = resultBasicMetadata.getJdbcTypeHandler().getValue(resultBasicMetadata.getDataReader(), streamResultSet);
            Object o = map.put(resultBasicMetadata.getColumnName(), value);
            if (o != null) {
                throw new IllegalStateException("Duplicate key found: " + resultBasicMetadata.getColumnName());
            }
        }
        return map;
    }
}
