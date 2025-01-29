package com.easy.query.core.basic.api.database;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/1/26 08:36
 * 数据库代码先行接口
 *
 * @author xuejiaming
 */
public interface DatabaseCodeFirst {
//    void createDatabaseIfNotExists();
    /**
     * 表是否存在
     * @param entity 数据库对象
     * @return true存在，false不存在
     */
    boolean tableExists(Class<?> entity);

    /**
     * 表是否存在
     * @param entities 数据库对象集合
     * @return 返回表和是否存在的map结构
     */
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

    /**
     * 创建表
     * @param entities 数据库对象集合
     * @return 可创建的表的执行对象
     */
    CodeFirstExecutable createTables(List<Class<?>> entities);

    /**
     * 删除表
     * @param entities 数据库对象集合
     * @return 可删除的表的执行对象
     */
    CodeFirstExecutable dropTables(List<Class<?>> entities);

    /**
     * 自动同步表结构
     * 如果数据库不存在则创建数据库(oracle不支持)
     * 如果表不存在则创建表
     * 如果表存在且class内的属性比数据库列多则自动生成添加列
     * 如果列或者表添加oldName则自动生成rename命令
     * @param entities 数据库对象集合
     * @return 可同步的表的执行对象
     */
    CodeFirstExecutable syncTables(List<Class<?>> entities);
}
