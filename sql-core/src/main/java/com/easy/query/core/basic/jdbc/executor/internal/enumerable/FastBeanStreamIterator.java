package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.util.EasyTrackUtil;

import java.sql.SQLException;

/**
 * create time 2023/7/31 16:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class FastBeanStreamIterator<T> extends DefaultBeanStreamIterator<T> {

    public FastBeanStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);
    }

    @Override
    protected void init0() throws SQLException {
        this.dataReader = resultMetadata.getDataReader();
        this.trackBean = EasyTrackUtil.trackBean(context, resultMetadata.getResultClass());
        this.trackManager = trackBean ? context.getRuntimeContext().getTrackManager() : null;
    }
}
