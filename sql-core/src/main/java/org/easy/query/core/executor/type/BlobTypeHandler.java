package org.easy.query.core.executor.type;

import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: BlobTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:38
 * @Created by xuejiaming
 */
public class BlobTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getBlob(resultSet.getIndex());
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
