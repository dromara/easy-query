package com.easy.query.core.basic.api.database;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationContext;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.core.migration.data.TableMigrationData;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * create time 2025/1/26 08:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDatabaseCodeFirst implements DatabaseCodeFirst {
    private final ServiceProvider serviceProvider;

    public DefaultDatabaseCodeFirst(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    protected QueryRuntimeContext getRuntimeContext() {
        return serviceProvider.getService(QueryRuntimeContext.class);
    }

    @Override
    public void createDatabaseIfNotExists(@Nullable Function<DataSource, Credentials> jdbcCredentialsByDataSourceFunction) {
        DatabaseMigrationProvider service = getRuntimeContext().getService(DatabaseMigrationProvider.class);
        service.createDatabaseIfNotExists(jdbcCredentialsByDataSourceFunction);
    }

    @Override
    public boolean tableExists(Class<?> entityClass) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        EntityMetadata entityMetadata = getRuntimeContext().getEntityMetadataManager().getEntityMetadata(entityClass);
        return migrationsSQLGenerator.tableExists(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName());
    }

    @Override
    public CodeFirstCommand createTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        List<TableMigrationData> tableMigrationDataList = entities.stream().map(o -> migrationsSQLGenerator.parseEntity(o)).collect(Collectors.toList());
        return createTableCommandByMigrationData(tableMigrationDataList);
    }

    @Override
    public CodeFirstCommand dropTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        List<TableMigrationData> tableMigrationDataList = entities.stream().map(o -> migrationsSQLGenerator.parseEntity(o)).collect(Collectors.toList());
        return dropTableCommandByMigrationData(tableMigrationDataList);
    }

    @Override
    public CodeFirstCommand dropTableIfExistsCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        List<TableMigrationData> tableMigrationDataList = entities.stream().map(o -> migrationsSQLGenerator.parseEntity(o)).collect(Collectors.toList());
        return dropTableIfExistsCommandByMigrationData(tableMigrationDataList);
    }

    @Override
    public CodeFirstCommand syncTableCommand(List<Class<?>> entities) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        List<TableMigrationData> tableMigrationDataList = entities.stream().map(o -> migrationsSQLGenerator.parseEntity(o)).collect(Collectors.toList());
        return syncTableCommandByMigrationData(tableMigrationDataList);
    }

    @Override
    public CodeFirstCommand createCodeFirstCommand(List<MigrationCommand> migrationCommands) {
        return new DefaultCodeFirstCommand(getRuntimeContext(), migrationCommands);
    }


    @Override
    public boolean tableExistsByMigrationData(TableMigrationData tableMigrationData) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        return migrationsSQLGenerator.tableExists(tableMigrationData.getSchema(), tableMigrationData.getTableName());
    }

    @Override
    public CodeFirstCommand createTableCommandByMigrationData(List<TableMigrationData> tableMigrationDataList) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(tableMigrationDataList);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateCreateTableMigrationSQL(migrationContext);
        return createCodeFirstCommand(migrationCommands);
    }

    @Override
    public CodeFirstCommand dropTableCommandByMigrationData(List<TableMigrationData> tableMigrationDataList) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(tableMigrationDataList);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateDropTableMigrationSQL(migrationContext, false);
        return createCodeFirstCommand(migrationCommands);
    }

    @Override
    public CodeFirstCommand dropTableIfExistsCommandByMigrationData(List<TableMigrationData> tableMigrationDataList) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(tableMigrationDataList);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateDropTableMigrationSQL(migrationContext, true);
        return createCodeFirstCommand(migrationCommands);
    }

    @Override
    public CodeFirstCommand syncTableCommandByMigrationData(List<TableMigrationData> tableMigrationDataList) {
        MigrationsSQLGenerator migrationsSQLGenerator = getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(tableMigrationDataList);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        return createCodeFirstCommand(migrationCommands);
    }
}
