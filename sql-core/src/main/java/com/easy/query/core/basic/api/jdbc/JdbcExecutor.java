package com.easy.query.core.basic.api.jdbc;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @FileName: EasyJDBC.java
 * @Description: 文件说明
 * @Date: 2023/3/12 22:41
 * @author xuejiaming
 */
public interface JdbcExecutor {
//   default  <T> List<T> sqlQuery(String sql,Class<T> clazz){
//       return sqlQuery(sql,clazz, Collections.emptyList());
//   }
//    default List<Map<String,Object>> sqlQueryMap(String sql){
//       return sqlQueryMap(sql,Collections.emptyList());
//    }
   default List<Map<String,Object>> sqlQueryMap(String sql,List<Object> parameters){
       List<SQLParameter> sqlParameters = EasyCollectionUtil.map(parameters, o -> new EasyConstSQLParameter(null, null, o));
       List maps = sqlQuery(sql, Map.class, sqlParameters);
       return (List<Map<String,Object>>)maps;
   }
//    default <T> List<T> sqlQuery(String sql,Class<T> clazz,List<Object> parameters){
//        List<SQLParameter> sqlParameters = EasyCollectionUtil.map(parameters, o -> new EasyConstSQLParameter(null, null, o));
//        return sqlQuery(sql,clazz,sqlParameters);
//    }
    <T> List<T> sqlQuery(String sql,Class<T> clazz,List<SQLParameter> parameters);
//    default long sqlExecute(String sql){
//        return sqlExecute(sql,Collections.emptyList());
//    }
    long sqlExecute(String sql,List<Object> parameters);
}
