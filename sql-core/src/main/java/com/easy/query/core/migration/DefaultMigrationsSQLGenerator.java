package com.easy.query.core.migration;

import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2025/1/11 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationsSQLGenerator implements MigrationsSQLGenerator {
    private final EntityMetadataManager entityMetadataManager;
    private final DatabaseMigrationProvider databaseMigrationProvider;

    public DefaultMigrationsSQLGenerator(EntityMetadataManager entityMetadataManager, DatabaseMigrationProvider databaseMigrationProvider) {
        this.entityMetadataManager = entityMetadataManager;
        this.databaseMigrationProvider = databaseMigrationProvider;
    }

    @Override
    public List<MigrationCommand> generateMigrationSQL(MigrationContext migrationContext) {
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        if (!databaseMigrationProvider.databaseExists()) {
            MigrationCommand databaseCommand = databaseMigrationProvider.createDatabaseCommand();
            if (databaseCommand != null) {
                migrationCommands.add(databaseCommand);
            }
        }
        for (Class<?> entity : migrationContext.getEntities()) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity);
            EntityMigrationMetadata entityMigrationMetadata = databaseMigrationProvider.createEntityMigrationMetadata(entityMetadata);
            if (EasyStringUtil.isBlank(entityMetadata.getTableName())) {
                throw new EasyQueryInvalidOperationException(String.format("class type:[%s] not found [@Table] annotation.", EasyClassUtil.getSimpleName(entityMetadata.getEntityClass())));
            }
//            if (sql.length() > 0) {
//                sql.append(newLine);
//            }
//            String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null, null);
//            String oldTableName = EasyStringUtil.isBlank(entityMetadata.getOldTableName()) ? null : EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getOldTableName(), null, null);
            boolean tableExists = databaseMigrationProvider.tableExists(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName());
            if (!tableExists) {
                if (!Objects.equals(entityMetadata.getTableName(), entityMetadata.getOldTableName())) {
                    boolean oldTableExists = databaseMigrationProvider.tableExists(entityMetadata.getSchemaOrNull(), entityMetadata.getOldTableName());
                    if (oldTableExists) {
                        MigrationCommand migrationCommand = databaseMigrationProvider.renameTable(entityMigrationMetadata);
                        if (migrationCommand != null) {
                            migrationCommands.add(migrationCommand);
                        }

                        List<MigrationCommand> columns = databaseMigrationProvider.syncTable(entityMigrationMetadata, true);
                        if (columns != null) {
                            migrationCommands.addAll(columns);
                        }
                    } else {
                        MigrationCommand migrationCommand = databaseMigrationProvider.createTable(entityMigrationMetadata);
                        if (migrationCommand != null) {
                            migrationCommands.add(migrationCommand);
                        }
                    }
                } else {
                    MigrationCommand migrationCommand = databaseMigrationProvider.createTable(entityMigrationMetadata);
                    if (migrationCommand != null) {
                        migrationCommands.add(migrationCommand);
                    }
                }
            } else {
                List<MigrationCommand> columns = databaseMigrationProvider.syncTable(entityMigrationMetadata, false);
                if (columns != null) {
                    migrationCommands.addAll(columns);
                }
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
}
