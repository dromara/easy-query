package org.easy.query.core.abstraction;

import org.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * @FileName: EasyExecutor.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:20
 * @Created by xuejiaming
 */
public interface EasyExecutor {
    <T> long update(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters);
    <T> long update(ExecutorContext executorContext,String sql,List<T> entities, List<SQLParameter> sqlParameters);
    <T> long insert(ExecutorContext executorContext,String sql, List<T> entities,List<SQLParameter> sqlParameters);
    <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, String sql, List<SQLParameter> sqlParameters);
}
