package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultBasicMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.BasicJdbcProperty;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.util.EasyClassUtil;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * create time 2023/7/31 17:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class DraftStreamIterator extends AbstractMapToStreamIterator<DraftResult> {
    private ResultSetMetaData rsmd;
    private ResultBasicMetadata[] resultBasicMetadatas;
    private int mapCount = -1;

    public DraftStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<DraftResult> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);
    }

    @Override
    protected void init0() throws SQLException {
        rsmd = streamResultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();//有多少列
        resultBasicMetadatas = new ResultBasicMetadata[columnCount];
    }

    private @Nullable Class<?> getDraftPropType(int index) {
        Class<?>[] draftPropTypes = context.getExpressionContext().getDraftPropTypes();
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
            resultBasicMetadatas = new ResultBasicMetadata[draft.capacity()];
        }
        for (int i = 0; i < resultBasicMetadatas.length; i++) {
            if(!draft.readColumn(i)){
                break;
            }

            if(mapCount==0){
                Class<?> propType = getDraftPropType(i);
                if(propType!=null){
                    JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(propType);
                    BasicJdbcProperty dataReader = new BasicJdbcProperty(i, propType);
                    resultBasicMetadatas[i] = new ResultBasicMetadata(null, dataReader, handler);
                }
            }
            ResultBasicMetadata resultBasicMetadata = resultBasicMetadatas[i];
            if(resultBasicMetadata==null){
                draft.setValues(i, streamResultSet.getObject(i + 1));
            }else{
                Object value = resultBasicMetadata.getJdbcTypeHandler().getValue(resultBasicMetadata.getDataReader(), streamResultSet);
                draft.setValues(i, value);
            }
        }
        return draft;
    }
}
