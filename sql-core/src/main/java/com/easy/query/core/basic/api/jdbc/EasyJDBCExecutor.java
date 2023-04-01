package com.easy.query.core.basic.api.jdbc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @FileName: EasyJDBC.java
 * @Description: 文件说明
 * @Date: 2023/3/12 22:41
 * @author xuejiaming
 */
public interface EasyJDBCExecutor {
   default  <T> List<T> sqlQuery(String sql,Class<T> clazz){
       return sqlQuery(sql,clazz, Collections.emptyList());
   }
    default List<Map<String,Object>> sqlQueryMap(String sql){
       return sqlQueryMap(sql,Collections.emptyList());
    }
   default List<Map<String,Object>> sqlQueryMap(String sql,List<Object> parameters){
       List maps = sqlQuery(sql, Map.class, parameters);
       return (List<Map<String,Object>>)maps;
   }
    <T> List<T> sqlQuery(String sql,Class<T> clazz,List<Object> parameters);
    default long sqlExecute(String sql){
        return sqlExecute(sql,Collections.emptyList());
    }
    long sqlExecute(String sql,List<Object> parameters);
}
