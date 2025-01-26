package com.easy.query.core.basic.api.database;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/1/26 08:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DatabaseCodeFirst {
    boolean tableExists(Class<?> entity);
   default Map<Class<?>,Boolean> tableExists(List<Class<?>> entities){
       if(EasyCollectionUtil.isEmpty(entities)){
           return new HashMap<>(0);
       }
       HashMap<Class<?>, Boolean> result = new HashMap<>(entities.size());
       for (Class<?> entity : entities) {
           boolean tableExists = tableExists(entity);
           result.put(entity,tableExists);
       }
       return result;
   }
    CodeFirstExecutable createTables(List<Class<?>> entities);

    CodeFirstExecutable dropTables(List<Class<?>> entities);

    CodeFirstExecutable syncTables(List<Class<?>> entities);
}
