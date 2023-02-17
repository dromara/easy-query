package org.easy.query.core.executor.type;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @FileName: DoubleTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:02
 * @Created by xuejiaming
 */
public class FloatTypeHandler implements JdbcTypeHandler{
    private static final float DEFAULT=0f;
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {

        ResultSet rs = resultSet.getRs();
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
}
