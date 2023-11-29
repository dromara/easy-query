package com.easy.query.core.basic.migration;

import com.easy.query.core.annotation.Nullable;

import java.util.function.Function;

/**
 * create time 2023/11/27 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableCreator<TEntity> {
//    void createTables(Class<?> entityClass);
    TableCreator<TEntity> asTable(String tableName);
    TableCreator<TEntity> asTable(Function<String,String> tableNameAs);
    TableCreator<TEntity> columnsConfigure();
    boolean migrate(@Nullable String fromMigration,@Nullable String toMigration);
    String generateScript(@Nullable String fromMigration,@Nullable String toMigration);
}
