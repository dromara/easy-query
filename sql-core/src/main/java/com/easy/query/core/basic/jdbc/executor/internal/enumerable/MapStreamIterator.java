package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.internal.props.BasicJdbcProperty;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultBasicMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.JdbcTypes;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.column2mapkey.Column2MapKeyConversion;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * create time 2023/7/31 17:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapStreamIterator<T> extends AbstractMapToStreamIterator<T> {
    private ResultSetMetaData rsmd;
    private ResultBasicMetadata[] resultBasicMetadatas;
    private int mapCount=-1;

    public MapStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);
    }

    @Override
    protected void init0() throws SQLException {
        rsmd = streamResultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();//有多少列
        resultBasicMetadatas=new ResultBasicMetadata[columnCount];
    }

    @Override
    protected T mapTo() throws SQLException {

        mapCount++;
        Class<T> clazz = resultMetadata.getResultClass();
        EasyQueryOption easyQueryOption1 = context.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
        Map<String, Object> map = EasyClassUtil.newMapInstanceOrNull(clazz);
        if (map == null) {
            throw new SQLException("cant create map:" + EasyClassUtil.getSimpleName(clazz));
        }
        JdbcTypeHandlerManager easyJdbcTypeHandler = context.getRuntimeContext().getJdbcTypeHandlerManager();
        Column2MapKeyConversion column2MapKeyConversion = context.getRuntimeContext().getColumn2MapKeyConversion();

        for (int i = 0; i < resultBasicMetadatas.length; i++) {
            if(mapCount==0){
                String colName = getColName(rsmd, i + 1);//数据库查询出来的列名
                int columnType = rsmd.getColumnType(i + 1);
                Class<?> propertyType = JdbcTypes.jdbcJavaTypes.get(columnType);
                JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(propertyType);
                BasicJdbcProperty dataReader = new BasicJdbcProperty(i, propertyType);
                resultBasicMetadatas[i]=new ResultBasicMetadata(colName,dataReader,handler);
            }
            ResultBasicMetadata resultBasicMetadata = resultBasicMetadatas[i];
            Object value = resultBasicMetadata.getJdbcTypeHandler().getValue(resultBasicMetadata.getDataReader(),streamResultSet);
            Object o = map.put(column2MapKeyConversion.convertToMapKey(resultBasicMetadata.getColumnName()), value);
            if (o != null) {
                throw new IllegalStateException("Duplicate key found: " + resultBasicMetadata.getColumnName());
            }
        }
        return EasyObjectUtil.typeCastNullable(map);
    }
}
