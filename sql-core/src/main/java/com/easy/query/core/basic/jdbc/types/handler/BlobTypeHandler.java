package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * @FileName: BlobTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:38
 * @author xuejiaming
 */
public class BlobTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(DataReader dataReader, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getBlob(dataReader.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setBlob(parameter.getIndex(),(Blob) parameter.getValue());
    }

}
