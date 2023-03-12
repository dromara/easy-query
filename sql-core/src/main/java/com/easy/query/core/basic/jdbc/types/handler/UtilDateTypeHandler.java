package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * @FileName: DateTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:56
 * @Created by xuejiaming
 */
public class UtilDateTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getRs().getTimestamp(resultSet.getIndex());
        if(timestamp!=null){
            return new java.util.Date(timestamp.getTime());
        }
        return null;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        java.util.Date date = (java.util.Date) parameter.getValue();
        Timestamp timestamp =  date==null?null:new Timestamp(date.getTime());
        parameter.getPs().setTimestamp(parameter.getIndex(),timestamp);
    }

    @Override
    public int getJdbcType() {
        return Types.DATE;
    }
}
