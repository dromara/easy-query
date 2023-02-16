package org.easy.query.core.abstraction;

import org.easy.query.core.config.EasyConnector;
import org.easy.query.core.config.EasyQueryConfiguration;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.util.ClassUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: DefaultExecutor.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:49
 * @Created by xuejiaming
 */
public class DefaultExecutor implements EasyExecutor{
    @Override
    public <TR> List<TR> execute(ExecutorContext executorContext,Class<TR> clazz, String sql, List<Object> parameters) {
        EasyConnector easyConnector = executorContext.getRuntimeContext().getEasyConnector();
        Connection connection = null;
        List<TR> result=null;
        try {
            System.out.println("开始执行："+sql);
            connection=easyConnector.getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                int paramSize = parameters.size();
                for (int i = 0; i < paramSize; i++) {
                    preparedStatement.setObject(i+1,parameters.get(i));
                }
                try(ResultSet rs = preparedStatement.executeQuery()){
                    ResultSetMetaData metaData = rs.getMetaData();
                    result=new ArrayList<>();
                    while(rs.next()){
                        System.out.println(metaData.getColumnName(1)+"---"+metaData.getColumnName(2));
                        System.out.println(rs.getObject(1)+"---"+rs.getObject(2));
                    }
                }
            }
        } catch (SQLException e) {
            throw new JDQCException(e);
        }
        finally {
            easyConnector.closeConnection(connection);
        }
        result.add(ClassUtil.newInstance(clazz));
        return result;
    }
}
