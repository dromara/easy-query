package com.easy.query.core.basic.jdbc.executor.internal.reader;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;

/**
 * create time 2023/8/27 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class BeanDataReader implements DataReader {
    private final DataReader previousDataReader;
    private final DataReader nextDataReader;

    public BeanDataReader(DataReader previousDataReader, DataReader nextDataReader){
        this.previousDataReader = previousDataReader;
        this.nextDataReader = nextDataReader;
    }

    @Override
    public <TBean> void readTo(TBean entity, StreamResultSet streamResultSet) throws SQLException {
        previousDataReader.readTo(entity,streamResultSet);
        nextDataReader.readTo(entity,streamResultSet);
    }
}
