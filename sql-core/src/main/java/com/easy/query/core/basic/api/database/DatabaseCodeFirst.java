package com.easy.query.core.basic.api.database;

import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyCollectionUtil;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * create time 2025/1/26 08:36
 * 数据库代码先行接口
 *
 * @author xuejiaming
 */
public interface DatabaseCodeFirst {
    /**
     * 如果数据库不存在则创建数据库(oracle、dameng不支持)
     */
    default void createDatabaseIfNotExists() {
        createDatabaseIfNotExists(null);
    }

    /**
     * 如果数据库不存在则创建数据库(oracle、dameng不支持)
     *
     * @param jdbcUrlByDataSourceFunction 根据datasource获取jdbc url
     */
    void createDatabaseIfNotExists(@Nullable Function<DataSource, String> jdbcUrlByDataSourceFunction);

    /**
     * 表是否存在
     *
     * @param entity 数据库对象
     * @return true存在，false不存在
     */
    boolean tableExists(Class<?> entity);

    /**
     * 表是否存在
     *
     * @param entities 数据库对象集合
     * @return 返回表和是否存在的map结构
     */
    default Map<Class<?>, Boolean> tableExists(List<Class<?>> entities) {
        if (EasyCollectionUtil.isEmpty(entities)) {
            return new HashMap<>(0);
        }
        HashMap<Class<?>, Boolean> result = new HashMap<>(entities.size());
        for (Class<?> entity : entities) {
            boolean tableExists = tableExists(entity);
            result.put(entity, tableExists);
        }
        return result;
    }

    /**
     * 创建表
     *
     * @param entities 数据库对象集合
     * @return 可创建的表的执行对象
     */
    CodeFirstCommand createTableCommand(List<Class<?>> entities);

    /**
     * 删除表
     *
     * @param entities 数据库对象集合
     * @return 可删除的表的执行对象
     */
    CodeFirstCommand dropTableCommand(List<Class<?>> entities);

    /**
     * 删除表如果存在的话
     *
     * @param entities 数据库对象集合
     * @return 可删除的表的执行对象
     */
    CodeFirstCommand dropTableIfExistsCommand(List<Class<?>> entities);

    /**
     * 自动同步表结构
     * 如果数据库不存在则创建数据库(oracle不支持)
     * 如果表不存在则创建表
     * 如果表存在且class内的属性比数据库列多则自动生成添加列
     * 如果列或者表添加oldName则自动生成rename命令
     * 同步表命令,不存在生成,存在判断列是否需要额外增加列、索引、外键是否需要重命名表名和列名
     *
     * @param entities 数据库对象集合
     * @return 可同步的表的执行对象
     */
    CodeFirstCommand syncTableCommand(List<Class<?>> entities);


    boolean tableExistsByMigrationData(TableMigrationData tableMigrationData);

    /**
     * 表是否存在
     *
     * @param tableMigrationDataList 数据库对象集合
     * @return 返回表和是否存在的map结构
     */
    default Map<String, Boolean> tableExistsByMigrationData(List<TableMigrationData> tableMigrationDataList) {
        if (EasyCollectionUtil.isEmpty(tableMigrationDataList)) {
            return new HashMap<>(0);
        }
        HashMap<String, Boolean> result = new HashMap<>(tableMigrationDataList.size());
        for (TableMigrationData tableMigrationData : tableMigrationDataList) {
            boolean tableExists = tableExistsByMigrationData(tableMigrationData);
            result.put(tableMigrationData.getTableName(), tableExists);
        }
        return result;
    }

    /**
     * 创建表命令
     *
     * @param tableMigrationDataList
     * @return
     */
    CodeFirstCommand createTableCommandByMigrationData(List<TableMigrationData> tableMigrationDataList);

    /**
     * 删除表命令
     *
     * @param tableMigrationDataList
     * @return
     */
    CodeFirstCommand dropTableCommandByMigrationData(List<TableMigrationData> tableMigrationDataList);

    /**
     * 删除表命令(判断存在才生成)
     *
     * @param tableMigrationDataList
     * @return
     */
    CodeFirstCommand dropTableIfExistsCommandByMigrationData(List<TableMigrationData> tableMigrationDataList);

    /**
     * 同步表命令,不存在生成,存在判断列是否需要额外增加列、索引、外键是否需要重命名表名和列名
     *
     * @param tableMigrationDataList
     * @return
     */
    CodeFirstCommand syncTableCommandByMigrationData(List<TableMigrationData> tableMigrationDataList);


    CodeFirstCommand createCodeFirstCommand(List<MigrationCommand> migrationCommands);

}
