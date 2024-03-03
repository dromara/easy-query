package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultBasicMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.BasicJdbcProperty;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create time 2023/7/31 17:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapTypeStreamIterator extends AbstractMapToStreamIterator<Map<String,Object>> {
    private ResultSetMetaData rsmd;
    private ResultBasicMetadata[] resultBasicMetadatas;
    private int mapCount = -1;

    public MapTypeStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<Map<String,Object>> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);
    }

    @Override
    protected void init0() throws SQLException {
        rsmd = streamResultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();//有多少列
        resultBasicMetadatas = new ResultBasicMetadata[columnCount];
    }

    private Class<?> getPropTypeOrObjectType(int index) {
        Class<?>[] mapPropTypes = context.getExpressionContext().getResultPropTypes();
        if (mapPropTypes != null) {
            return mapPropTypes[index];
        }
        return Object.class;
    }

    @Override
    protected Map<String,Object> mapTo() throws SQLException {
        mapCount++;
        int length = context.getExpressionContext().getResultPropTypes().length;
        Map<String,Object> map = new LinkedHashMap<String,Object>(length);
        JdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getJdbcTypeHandlerManager();

        if (mapCount == 0) {
            resultBasicMetadatas = new ResultBasicMetadata[length];
        }
        for (int i = 0; i < resultBasicMetadatas.length; i++) {

            if(mapCount==0){
                Class<?> propType = getPropTypeOrObjectType(i);
                String colName = getColName(rsmd, i + 1);//数据库查询出来的列名
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
