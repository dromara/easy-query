package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: BlobTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:38
 * @author xuejiaming
 */
public class BlobTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getStreamResult().getBlob(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setBlob(parameter.getIndex(),(Blob) parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.BLOB;
    }
}
