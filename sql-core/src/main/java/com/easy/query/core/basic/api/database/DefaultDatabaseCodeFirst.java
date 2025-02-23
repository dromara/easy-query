package com.easy.query.core.basic.api.database;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.inject.ServiceProvider;
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
    private final ServiceProvider serviceProvider;

    public DefaultDatabaseCodeFirst(ServiceProvider serviceProvider){
        this.serviceProvider = serviceProvider;
    }

    protected QueryRuntimeContext getRuntimeContext(){
        return serviceProvider.getService(QueryRuntimeContext.class);
    }

    @Override
    public void createDatabaseIfNotExists() {
        DatabaseMigrationProvider service = getRuntimeContext().getService(DatabaseMigrationProvider.class);
        service.createDatabaseIfNotExists();
    }

    @Override
    public boolean tableExists(Class<?> entity) {
        return false;
    }

    @Override
    public CodeFirstCommand createTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(entities);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateCreateTableMigrationSQL(migrationContext);
        return createCodeFirstCommand(migrationCommands);
    }

    @Override
    public CodeFirstCommand dropTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(entities);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateDropTableMigrationSQL(migrationContext);
        return createCodeFirstCommand(migrationCommands);
    }

    @Override
    public CodeFirstCommand syncTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(entities);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        return createCodeFirstCommand(migrationCommands);
    }

    @Override
    public CodeFirstCommand createCodeFirstCommand(List<MigrationCommand> migrationCommands) {
        return new DefaultCodeFirstCommand(getRuntimeContext(),migrationCommands);
    }
}
