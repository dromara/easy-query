package com.easy.query.core.basic.api.database;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationContext;
import com.easy.query.core.migration.MigrationsSQLGenerator;

import java.util.List;

/**
 * create time 2025/1/26 08:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDatabaseCodeFirst implements DatabaseCodeFirst{
    private final QueryRuntimeContext runtimeContext;

    public DefaultDatabaseCodeFirst(QueryRuntimeContext runtimeContext){
        this.runtimeContext = runtimeContext;
    }


    @Override
    public void createDatabaseIfNotExists() {
        DatabaseMigrationProvider service = runtimeContext.getService(DatabaseMigrationProvider.class);
        service.createDatabaseIfNotExists();
    }

    @Override
    public boolean tableExists(Class<?> entity) {
        return false;
    }

    @Override
    public CodeFirstCommand createTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = runtimeContext.getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(entities);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateCreateTableMigrationSQL(migrationContext);
        return new DefaultCodeFirstCommand(runtimeContext,migrationCommands);
    }

    @Override
    public CodeFirstCommand dropTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = runtimeContext.getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(entities);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateDropTableMigrationSQL(migrationContext);
        return new DefaultCodeFirstCommand(runtimeContext,migrationCommands);
    }

    @Override
    public CodeFirstCommand syncTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = runtimeContext.getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(entities);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        return new DefaultCodeFirstCommand(runtimeContext,migrationCommands);
    }
}
