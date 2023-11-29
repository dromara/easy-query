package com.easy.query.core.basic.migration;

/**
 * create time 2023/11/27 22:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Migrator {
    DatabaseCreator database();
    <T> TableCreator<T> table(Class<T> entityClass);
}
