package org.easy.query.core.executor.type;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @FileName: DoubleTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:02
 * @Created by xuejiaming
 */
public class DoubleTypeHandler implements JdbcTypeHandler{
    private static final double DEFAULT=0d;
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {

        ResultSet rs = resultSet.getRs();
        double r = rs.getDouble(resultSet.getIndex());
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
parameter.getPs().setDouble(parameter.getIndex(),(Double)parameter.getValue());
    }
}
