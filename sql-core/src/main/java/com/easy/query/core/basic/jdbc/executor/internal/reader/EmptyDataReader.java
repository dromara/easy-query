package com.easy.query.core.basic.jdbc.executor.internal.reader;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;

/**
 * create time 2023/8/28 08:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyDataReader implements DataReader{
    public static final DataReader EMPTY=new EmptyDataReader();
    @Override
    public <TBean> void readTo(TBean entity, StreamResultSet streamResultSet) throws SQLException {

    }
}
