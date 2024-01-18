package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.util.EasyObjectUtil;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * create time 2023/7/31 16:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultStreamIterable<T> implements StreamIterable<T> {
    private final ExecutorContext context;
    private final ResultMetadata<T> resultMetadata;
    private final StreamResultSet streamResultSet;

    public DefaultStreamIterable(ExecutorContext context, ResultMetadata<T> resultMetadata, StreamResultSet streamResultSet) {

        this.context = context;
        this.resultMetadata = resultMetadata;
        this.streamResultSet = streamResultSet;
    }

    @Override
    public Iterator<T> iterator() {
        try {
            return iterator0();
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }

    private Iterator<T> iterator0() throws SQLException {
        EntityMetadataTypeEnum entityMetadataType = resultMetadata.getEntityMetadataType();
        switch (entityMetadataType) {
            case MAP:
                return new MapStreamIterator<>(context, streamResultSet, resultMetadata);
            case BASIC_TYPE:
                return new BasicStreamIterator<>(context, streamResultSet, resultMetadata);
//            case DRAFT:
//                return EasyObjectUtil.typeCastNullable(new DraftStreamIterator(context, streamResultSet, EasyObjectUtil.typeCastNullable(resultMetadata)));
            default:{
                if(resultMetadata.getDataReader()!=null){
                    return new FastBeanStreamIterator<>(context, streamResultSet, resultMetadata);
                }
                if(DraftResult.class.isAssignableFrom(resultMetadata.getResultClass())){
                    return EasyObjectUtil.typeCastNullable(new DraftStreamIterator(context, streamResultSet, EasyObjectUtil.typeCastNullable(resultMetadata)));
                }
                return new DefaultBeanStreamIterator<>(context, streamResultSet, resultMetadata);
            }
        }
    }
}
