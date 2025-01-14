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
import java.util.Collections;
import java.util.List;

/**
 * create time 2025/1/11 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationsSQLGenerator implements MigrationsSQLGenerator {
    private static final String newLine = System.lineSeparator();
    private final EntityMetadataManager entityMetadataManager;
    private final SQLKeyword sqlKeyword;
    private final DataSource dataSource;

    public DefaultMigrationsSQLGenerator(EntityMetadataManager entityMetadataManager, QueryConfiguration queryConfiguration,DataSource dataSource) {
        this.entityMetadataManager = entityMetadataManager;
        this.sqlKeyword = queryConfiguration.getDialect();
        this.dataSource = dataSource;
    }

    @Override
    public MigrationInfoConverter createConverter(MigrationContext migrationContext) {
        return new DefaultDatabaseMigrationInfoConverter(migrationContext.getRuntimeContext());
    }

    @Override
    public List<MigrationCommand> generateMigrationSQL(MigrationContext migrationContext) {
        MigrationInfoConverter migrationInfoConverter = createConverter(migrationContext);
        String databaseName = migrationInfoConverter.getDatabaseName(dataSource);
        StringBuilder databaseSQL = new StringBuilder();
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        if(!migrationInfoConverter.databaseExists(dataSource,databaseName)){
            databaseSQL.append("CREATE DATABASE IF NOT EXISTS ").append(sqlKeyword.getQuoteName(databaseName)).append(" default charset utf8mb4 COLLATE utf8mb4_0900_ai_ci;").append(newLine);
            migrationCommands.add(new DefaultMigrationCommand(null,databaseSQL.toString()));
        }
//        sql.append("CREATE DATABASE IF NOT EXISTS ").append(tableName).append(" default charset utf8mb4 COLLATE utf8mb4_0900_ai_ci;").append(newLine);
        for (Class<?> entity : migrationContext.getEntities()) {
            StringBuilder sql = new StringBuilder();
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity);
            if (EasyStringUtil.isBlank(entityMetadata.getTableName())) {
                throw new EasyQueryInvalidOperationException(String.format("class type:[%s] not found [@Table] annotation.", EasyClassUtil.getSimpleName(entityMetadata.getEntityClass())));
            }
            if (sql.length() > 0) {
                sql.append(newLine);
            }
            String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null, null);
            String oldTableName = EasyStringUtil.isBlank(entityMetadata.getOldTableName()) ? null : EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getOldTableName(), null, null);
            sql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" ( ");
            for (ColumnMetadata column : entityMetadata.getColumns()) {
                sql.append(newLine)
                        .append(sqlKeyword.getQuoteName(column.getName()))
                        .append(" ");
                ColumnTypeResult columnTypeResult = migrationInfoConverter.getColumnTypeResult(entityMetadata, column);
                sql.append(columnTypeResult.columnType);
                if (column.isGeneratedKey()) {
                    sql.append(" AUTO_INCREMENT");
                }
                String columnComment = migrationInfoConverter.getColumnComment(entityMetadata, column);
                if (EasyStringUtil.isNotBlank(columnComment)) {
                    sql.append(" COMMENT ").append(columnComment);
                }
                sql.append(",");
            }
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            if (EasyCollectionUtil.isNotEmpty(keyProperties)) {
                sql.append(" ").append(newLine).append(" PRIMARY KEY (");
                int i=keyProperties.size();
                for (String keyProperty : keyProperties) {
                    i--;
                    ColumnMetadata keyColumn = entityMetadata.getColumnNotNull(keyProperty);
                    sql.append(sqlKeyword.getQuoteName(keyColumn.getName()));
                    if(i>0){
                        sql.append(", ");
                    }else{
                        sql.append("),");
                    }
                }
            }
            sql.append(newLine).append(") Engine=InnoDB");
            String tableComment = migrationInfoConverter.getTableComment(entityMetadata);
            if(EasyStringUtil.isNotBlank(tableComment)){
                sql.append(" COMMENT=").append(tableComment);
            }
            sql.append(";").append(newLine);
            DefaultMigrationCommand defaultMigrationCommand = new DefaultMigrationCommand(entityMetadata, sql.toString());
            migrationCommands.add(defaultMigrationCommand);

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
