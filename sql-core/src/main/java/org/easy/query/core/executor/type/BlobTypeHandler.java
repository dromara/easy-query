package org.easy.query.core.executor.type;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

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
}
