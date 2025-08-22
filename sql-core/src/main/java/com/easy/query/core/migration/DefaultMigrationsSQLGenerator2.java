package com.easy.query.core.migration;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2025/1/11 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationsSQLGenerator2 implements MigrationsSQLGenerator {
    private final EntityMetadataManager entityMetadataManager;
    private final DatabaseMigrationProvider databaseMigrationProvider;
    private final ServiceProvider serviceProvider;

    public DefaultMigrationsSQLGenerator2(EntityMetadataManager entityMetadataManager, DatabaseMigrationProvider databaseMigrationProvider, ServiceProvider serviceProvider) {
        this.entityMetadataManager = entityMetadataManager;
        this.databaseMigrationProvider = databaseMigrationProvider;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public List<MigrationCommand> generateMigrationSQL(MigrationContext migrationContext) {
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
//        if (!databaseMigrationProvider.databaseExists()) {
//            MigrationCommand databaseCommand = databaseMigrationProvider.createDatabaseCommand();
//            if (databaseCommand != null) {
//                migrationCommands.add(databaseCommand);
//            }
//        }
        QueryRuntimeContext runtimeContext = serviceProvider.getService(QueryRuntimeContext.class);
        for (Class<?> entity : migrationContext.getEntities()) {
            TableMigrationData tableMigrationData=new TableMigrationData();
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity);
            EntityMigrationMetadata entityMigrationMetadata = databaseMigrationProvider.createEntityMigrationMetadata(entityMetadata);
            if (EasyStringUtil.isBlank(tableMigrationData.getTableName())) {
                throw new EasyQueryInvalidOperationException(String.format("table migration data key:[%s] not found table name.", tableMigrationData.getKey()));
            }
//            if (sql.length() > 0) {
//                sql.append(newLine);
//            }
//            String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null, null);
//            String oldTableName = EasyStringUtil.isBlank(entityMetadata.getOldTableName()) ? null : EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getOldTableName(), null, null);
            boolean tableExists = databaseMigrationProvider.tableExists(tableMigrationData.getSchema(), tableMigrationData.getTableName());
            if (!tableExists) {
                //如果新旧表名不一致
                if (!Objects.equals(tableMigrationData.getTableName(), tableMigrationData.getOldTableName())) {
                    //判断旧表是否存在
                    boolean oldTableExists = databaseMigrationProvider.tableExists(tableMigrationData.getSchema(), tableMigrationData.getOldTableName());
                    if (oldTableExists) {
                        //存在就要修改表名
                        MigrationCommand migrationCommand = databaseMigrationProvider.renameTable(entityMigrationMetadata);
                        if (migrationCommand != null) {
                            migrationCommands.add(migrationCommand);
                        }
                        //新增修改删除表信息
                        List<MigrationCommand> columns = databaseMigrationProvider.syncTable(entityMigrationMetadata, false);
                        if (columns != null) {
                            migrationCommands.addAll(columns);
                        }
                        //判断是否要创建索引
                    } else {
                        //表不存在就创建表
                        MigrationCommand migrationCommand = databaseMigrationProvider.createTable(entityMigrationMetadata);
                        if (migrationCommand != null) {
                            migrationCommands.add(migrationCommand);
                        }
                        //创建外键
                        List<MigrationCommand> tableForeignKeyCommands = databaseMigrationProvider.createTableForeignKey(entityMigrationMetadata, runtimeContext);
                        migrationCommands.addAll(tableForeignKeyCommands);
                        //创建索引
                        List<MigrationCommand> tableIndexCommands = databaseMigrationProvider.createTableIndex(entityMigrationMetadata);
                        migrationCommands.addAll(tableIndexCommands);
                    }
                } else {
                    //表不存在就创建表
                    MigrationCommand migrationCommand = databaseMigrationProvider.createTable(entityMigrationMetadata);
                    if (migrationCommand != null) {
                        migrationCommands.add(migrationCommand);
                    }
                    //创建外键
                    List<MigrationCommand> tableForeignKeyCommands = databaseMigrationProvider.createTableForeignKey(entityMigrationMetadata, runtimeContext);
                    migrationCommands.addAll(tableForeignKeyCommands);
                    //创建索引
                    List<MigrationCommand> tableIndexCommands = databaseMigrationProvider.createTableIndex(entityMigrationMetadata);
                    migrationCommands.addAll(tableIndexCommands);
                }
            } else {
                List<MigrationCommand> columns = databaseMigrationProvider.syncTable(entityMigrationMetadata, false);
                if (columns != null) {
                    migrationCommands.addAll(columns);
                }
                //创建外键
                List<MigrationCommand> tableForeignKeyCommands = databaseMigrationProvider.syncTableForeignKey(entityMigrationMetadata, runtimeContext, false);
                migrationCommands.addAll(tableForeignKeyCommands);
                //创建索引
                List<MigrationCommand> tableIndexCommands = databaseMigrationProvider.syncTableIndex(entityMigrationMetadata, false);
                migrationCommands.addAll(tableIndexCommands);
            }

//            for (ColumnMetadata column : entityMetadata.getColumns()) {
//
//            }
//            foreach (var tbcol in tb.ColumnsByPosition)
//            {
//                sb.Append(" \r\n  ").Append(_commonUtils.QuoteSqlName(tbcol.Attribute.Name)).Append(" ").Append(tbcol.Attribute.DbType);
//                if (tbcol.Attribute.IsIdentity == true && tbcol.Attribute.DbType.IndexOf("AUTO_INCREMENT", StringComparison.CurrentCultureIgnoreCase) == -1) sb.Append(" AUTO_INCREMENT");
//                if (string.IsNullOrEmpty(tbcol.Comment) == false) sb.Append(" COMMENT ").Append(_commonUtils.FormatSql("{0}", tbcol.Comment));
//                sb.Append(",");
//            }
//            if (tb.Primarys.Any())
//            {
//                sb.Append(" \r\n  PRIMARY KEY (");
//                foreach (var tbcol in tb.Primarys) sb.Append(_commonUtils.QuoteSqlName(tbcol.Attribute.Name)).Append(", ");
//                sb.Remove(sb.Length - 2, 2).Append("),");
//            }

        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> generateCreateTableMigrationSQL(MigrationContext migrationContext) {
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(migrationContext.getEntities().size());
        for (Class<?> entity : migrationContext.getEntities()) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity);
            EntityMigrationMetadata entityMigrationMetadata = new EntityMigrationMetadata(entityMetadata);
            MigrationCommand migrationCommand = databaseMigrationProvider.createTable(entityMigrationMetadata);
            if (migrationCommand != null) {
                migrationCommands.add(migrationCommand);
            }
            List<MigrationCommand> tableIndexCommands = databaseMigrationProvider.createTableIndex(entityMigrationMetadata);
            migrationCommands.addAll(tableIndexCommands);
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> generateDropTableMigrationSQL(MigrationContext migrationContext,boolean checkTableExists) {
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(migrationContext.getEntities().size());
        for (Class<?> entity : migrationContext.getEntities()) {
            if(checkTableExists){
                if(!tableExists(entity)){
                    continue;
                }
            }
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity);
            MigrationCommand migrationCommand = databaseMigrationProvider.dropTable(new EntityMigrationMetadata(entityMetadata));
            if (migrationCommand != null) {
                migrationCommands.add(migrationCommand);
            }
        }
        return migrationCommands;
    }

    @Override
    public boolean tableExists(Class<?> entityType) {
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityType);
        return databaseMigrationProvider.tableExists(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName());
    }
}
