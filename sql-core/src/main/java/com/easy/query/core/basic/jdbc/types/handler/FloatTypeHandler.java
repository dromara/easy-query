package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;

import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: DoubleTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:02
 * @author xuejiaming
 */
public class FloatTypeHandler implements JdbcTypeHandler {
    private static final float DEFAULT=0f;
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {

        StreamResult rs = resultSet.getStreamResult();
        float r = rs.getFloat(resultSet.getIndex());
        if(r!=DEFAULT){
            return r;
        }
        if(rs.wasNull()){//判断当前读取的列是否可以为null，因为基本类型存在默认值而包装类型存在null值
            if(resultSet.isPrimitive()){
                return DEFAULT;
            }else{
                return null;
            }
        }
        return r;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
parameter.getPs().setFloat(parameter.getIndex(),(Float) parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.FLOAT;
    }
}
