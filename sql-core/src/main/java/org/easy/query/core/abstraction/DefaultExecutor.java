package org.easy.query.core.abstraction;

import org.easy.query.core.config.EasyConnector;
import org.easy.query.core.config.EasyQueryConfiguration;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.executor.type.EasyParameter;
import org.easy.query.core.executor.type.JdbcTypeHandler;
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
        EasyQueryRuntimeContext runtimeContext = executorContext.getRuntimeContext();
        EasyConnector easyConnector = runtimeContext.getEasyConnector();
        EasyJdbcTypeHandler easyJdbcTypeHandler = runtimeContext.getEasyJdbcTypeHandler();
        List<TR> result=null;
        System.out.println("开始执行："+sql);
        try(Connection connection=easyConnector.getConnection();
            PreparedStatement ps =createPreparedStatement(connection,sql,parameters,easyJdbcTypeHandler);
            ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            result=new ArrayList<>();
            while(rs.next()){
                System.out.println(metaData.getColumnName(1)+"---"+metaData.getColumnName(2));
                System.out.println(rs.getObject(1)+"---"+rs.getObject(2));
            }
        } catch (SQLException e) {
            throw new JDQCException(e);
        }
        result.add(ClassUtil.newInstance(clazz));
        return result;
    }

    private PreparedStatement createPreparedStatement(Connection connection,String sql,List<Object> parameters,EasyJdbcTypeHandler easyJdbcTypeHandler) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        EasyParameter easyParameter = new EasyParameter(preparedStatement, parameters);
        int paramSize = parameters.size();
        for (int i = 0; i < paramSize; i++) {
            easyParameter.setIndex(i);
            JdbcTypeHandler handler = easyJdbcTypeHandler.getHandler(easyParameter.getValue().getClass());
            handler.setParameter(easyParameter);
        }
        return preparedStatement;
    }
}
