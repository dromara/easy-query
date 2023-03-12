package com.easy.query.core.basic.api.jdbc;

import java.util.Collections;
import java.util.List;

/**
 * @FileName: EasyJDBC.java
 * @Description: 文件说明
 * @Date: 2023/3/12 22:41
 * @Created by xuejiaming
 */
public interface EasyJDBCExecutor {
   default  <T> List<T> sqlQuery(String sql,Class<T> clazz){
       return sqlQuery(sql,clazz, Collections.emptyList());
   }
    <T> List<T> sqlQuery(String sql,Class<T> clazz,List<Object> parameters);
    default long sqlExecute(String sql){
        return sqlExecute(sql,Collections.emptyList());
    }
    long sqlExecute(String sql,List<Object> parameters);
}
