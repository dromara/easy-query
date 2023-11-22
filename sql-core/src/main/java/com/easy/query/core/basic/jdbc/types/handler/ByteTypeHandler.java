package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @author xuejiaming
 */
public class ByteTypeHandler implements JdbcTypeHandler {
    private static final byte DEFAULT=0;
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {

        byte r = streamResultSet.getByte(jdbcProperty.getJdbcIndex());
        if(streamResultSet.wasNull()){//判断当前读取的列是否可以为null，因为基本类型存在默认值而包装类型存在null值
            if(jdbcProperty.isPrimitive()){
                return DEFAULT;
            }else{
                return null;
            }
        }
        return r;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setByte(parameter.getIndex(),(byte)parameter.getValue());
    }

}
