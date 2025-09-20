package com.easy.query.core.migration;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.data.TableMigrationData;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.util.List;
import java.util.function.Function;

/**
 * create time 2025/1/14 13:29
 * 生成数据库迁移提供者
 *
 * @author xuejiaming
 */
public interface DatabaseMigrationProvider {
    void setMigrationParser(MigrationEntityParser migrationParser);

    MigrationEntityParser getMigrationEntityParser();

    EntityMigrationMetadata createEntityMigrationMetadata(EntityMetadata entityMetadata);

    String getDatabaseName();

    //    boolean databaseExists();
//
//    MigrationCommand createDatabaseCommand();

    /**
     * 创建数据库如果数据库不存在
     */
    default void createDatabaseIfNotExists() {
        createDatabaseIfNotExists(null);
    }

    /**
     * 创建数据库如果数据库不存在,手动指定如何通过数据源获取JDBC URL
     *
     * @param jdbcUrlByDataSourceFunction
     */
    void createDatabaseIfNotExists(@Nullable Function<DataSource, String> jdbcUrlByDataSourceFunction);

    boolean tableExists(String schema, String tableName);

    MigrationCommand renameTable(TableMigrationData tableMigrationData);

    MigrationCommand createTable(TableMigrationData tableMigrationData);

    List<MigrationCommand> createTableIndex(TableMigrationData tableMigrationData);

    List<MigrationCommand> createTableForeignKey(TableMigrationData tableMigrationData, QueryRuntimeContext runtimeContext);

    List<MigrationCommand> syncTable(TableMigrationData tableMigrationData, boolean oldTable);

    List<MigrationCommand> syncTableIndex(TableMigrationData tableMigrationData, boolean oldTable);

    List<MigrationCommand> syncTableForeignKey(TableMigrationData tableMigrationData, QueryRuntimeContext runtimeContext, boolean oldTable);

    MigrationCommand dropTable(TableMigrationData tableMigrationData);

}
