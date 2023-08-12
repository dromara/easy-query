package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @FileName: BooleanTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:39
 * @author xuejiaming
 */
public class BooleanTypeHandler implements JdbcTypeHandler {
    private static final boolean DEFAULT=false;
    @Override
    public Object getValue(DataReader dataReader, StreamResultSet streamResultSet) throws SQLException {

        boolean r = streamResultSet.getBoolean(dataReader.getJdbcIndex());
        if(streamResultSet.wasNull()){//判断当前读取的列是否可以为null，因为基本类型存在默认值而包装类型存在null值
            if(dataReader.isPrimitive()){
                return DEFAULT;
            }else{
                return null;
            }
        }
        return r;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setBoolean(parameter.getIndex(),(Boolean) parameter.getValue());
    }
}
