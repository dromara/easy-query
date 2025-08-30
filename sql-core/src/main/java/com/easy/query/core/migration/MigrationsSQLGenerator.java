package com.easy.query.core.migration;

import com.easy.query.core.migration.data.TableMigrationData;

import java.util.List;
import java.util.Map;

/**
 * create time 2025/1/11 13:49
 * feature:<a href="https://github.com/dromara/easy-query/issues/12">code-first</a>
 *
 * @author xuejiaming
 */
public interface MigrationsSQLGenerator {

    List<MigrationCommand> generateMigrationSQL(MigrationContext migrationContext);

    List<MigrationCommand> generateCreateTableMigrationSQL(MigrationContext migrationContext);
    List<MigrationCommand> generateDropTableMigrationSQL(MigrationContext migrationContext,boolean checkTableExists);
    boolean tableExists(String schema,String tableName);
    TableMigrationData parseEntity(Class<?> entityClass);
}
