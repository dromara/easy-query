package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * @FileName: EasyExecutor.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:20
 * @Created by xuejiaming
 */
public interface EasyExecutor {
    <T> long executeRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters);
    <T> long executeRows(ExecutorContext executorContext, String sql, List<T> entities, List<SQLParameter> sqlParameters);
    <T> long insert(ExecutorContext executorContext,String sql, List<T> entities,List<SQLParameter> sqlParameters,boolean fillAutoIncrement);
    <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, String sql, List<SQLParameter> sqlParameters);
}
