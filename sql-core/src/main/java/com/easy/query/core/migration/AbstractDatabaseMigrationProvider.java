package com.easy.query.core.migration;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create time 2025/1/18 20:09
 * 抽象生成数据库迁移提供者
 *
 * @author xuejiaming
 */
public abstract class AbstractDatabaseMigrationProvider implements DatabaseMigrationProvider {
    public static final String newLine = System.lineSeparator();
    protected final DataSource dataSource;
    protected final SQLKeyword sqlKeyword;

    protected String databaseName;
    protected MigrationEntityParser migrationEntityParser;


    public AbstractDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword) {
        this.dataSource = dataSource;
        this.sqlKeyword = sqlKeyword;
        this.migrationEntityParser = new DefaultMigrationEntityParser();
    }

    @Override
    public void setMigrationParser(MigrationEntityParser migrationParser) {
        this.migrationEntityParser = migrationParser;
    }

    @Override
    public EntityMigrationMetadata createEntityMigrationMetadata(EntityMetadata entityMetadata) {
        return new EntityMigrationMetadata(entityMetadata);
    }

    @Override
    public String getDatabaseName() {
        if (databaseName == null) {
            this.databaseName = EasyDatabaseUtil.getDatabaseName(dataSource, null);
        }
        if (databaseName == null) {
         throw new EasyQueryInvalidOperationException("cant get database name, Please confirm that the database has been created and exists.");
        }
        return this.databaseName;
    }

    @Override
    public void createDatabaseIfNotExists() {
        EasyDatabaseUtil.checkAndCreateDatabase(dataSource, (databaseName) -> {
            this.databaseName = databaseName;
            return databaseExistSQL(databaseName);
        }, databaseName -> {
            return createDatabaseSQL(databaseName);
        });
    }

    //    @Override
    public abstract String databaseExistSQL(String databaseName);

    public abstract String createDatabaseSQL(String databaseName);
//
//    @Override
//    public MigrationCommand createDatabaseCommand() {
//        String databaseSQL = "CREATE DATABASE IF NOT EXISTS " + getQuoteSQLName(databaseName) + " ENGINE=Ordinary;";
//        return new DefaultMigrationCommand(null, databaseSQL);
//    }


    @Override
    public @NotNull ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        ColumnDbTypeResult columnDbType = migrationEntityParser.getColumnDbType(entityMigrationMetadata, columnMetadata);
        if (columnDbType != null) {
            return columnDbType;
        }
        Field declaredField = entityMigrationMetadata.getFieldByName(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        if (annotation != null) {
            String dbType = annotation.dbType();
            if (EasyStringUtil.isNotBlank(dbType)) {
                return new ColumnDbTypeResult(dbType, null);
            }
        }
        ColumnDbTypeResult columnDbType0 = getColumnDbType0(entityMigrationMetadata, columnMetadata);

        if (columnDbType0 == null) {
            throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(entityMigrationMetadata.getEntityMetadata().getEntityClass()) + "] field name:" + columnMetadata.getFieldName() + " not found column db type.");
        }
        return columnDbType0;
    }

    protected abstract ColumnDbTypeResult getColumnDbType0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

    @Override
    public String getColumnComment(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata, String quote) {
        String columnComment = getColumnComment0(entityMigrationMetadata, columnMetadata);
        if (EasyStringUtil.isNotBlank(columnComment)) {
            return quote + columnComment + quote;
        }
        return null;
    }

    public String getColumnComment0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        String columnComment = migrationEntityParser.getColumnComment(entityMigrationMetadata, columnMetadata);
        if (EasyStringUtil.isNotBlank(columnComment)) {
            return columnComment;
        }
        Field declaredField = entityMigrationMetadata.getFieldByName(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        if (annotation != null) {
            String comment = annotation.comment();
            if (EasyStringUtil.isNotBlank(comment)) {
                return comment;
            }
        }
        return null;
    }

    @Override
    public boolean isNullable(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        if (columnMetadata.isPrimary()) {
            return false;
        }
        Boolean nullable = migrationEntityParser.isNullable(entityMigrationMetadata, columnMetadata);
        if (nullable != null) {
            return nullable;
        }
        if (columnMetadata.getPropertyType().isPrimitive()) {
            return false;
        }
        return columnMetadata.isNullable();
    }

    @Override
    public boolean columnExistInDb(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        Boolean exist = migrationEntityParser.columnExistInDb(entityMigrationMetadata, columnMetadata);
        if (exist != null) {
            return exist;
        }

        Field declaredField = entityMigrationMetadata.getFieldByName(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        if (annotation != null) {
            return annotation.exist();
        }
        return true;
    }

    @Override
    public @Nullable String getTableComment(EntityMigrationMetadata entityMigrationMetadata, String quote) {
        String tableComment = getTableComment0(entityMigrationMetadata);
        if (EasyStringUtil.isNotBlank(tableComment)) {
            return quote + tableComment + quote;
        }
        return tableComment;
    }

    protected @Nullable String getTableComment0(EntityMigrationMetadata entityMigrationMetadata) {
        String tableComment = migrationEntityParser.getTableComment(entityMigrationMetadata);
        if (EasyStringUtil.isNotBlank(tableComment)) {
            return tableComment;
        }
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        Table annotation = entityMetadata.getEntityClass().getAnnotation(Table.class);
        if (annotation != null) {
            String comment = annotation.comment();
            if (EasyStringUtil.isNotBlank(comment)) {
                return comment;
            }
        }
        return null;
    }

    @Override
    public String getColumnRenameFrom(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        String columnRenameFrom = migrationEntityParser.getColumnRenameFrom(entityMigrationMetadata, columnMetadata);
        if (EasyStringUtil.isNotBlank(columnRenameFrom)) {
            return columnRenameFrom;
        }
        Field declaredField = entityMigrationMetadata.getFieldByName(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        if (annotation != null) {
            String renameFrom = annotation.renameFrom();
            if (EasyStringUtil.isNotBlank(renameFrom)) {
                return renameFrom;
            }
        }
        return null;
    }

    protected Set<String> getColumnNames(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();

        String columnTableName = EasyToSQLUtil.getSchemaTableName(sqlKeyword, entityMetadata, oldTable ? entityMetadata.getOldTableName() : entityMetadata.getTableName(), null, null);
        //比较差异
        return EasyDatabaseUtil.getColumns(dataSource, "select * from " + columnTableName + " where 1=2");
    }

    @Override
    public List<MigrationCommand> syncTable(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {

        //比较差异
        Set<String> tableColumns = getColumnNames(entityMigrationMetadata, oldTable);

        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        for (ColumnMetadata column : entityMetadata.getColumns()) {
            if (columnExistInDb(entityMigrationMetadata, column)) {
                if (!tableColumns.contains(column.getName())) {
                    String columnRenameFrom = getColumnRenameFrom(entityMigrationMetadata, column);
                    if (EasyStringUtil.isNotBlank(columnRenameFrom) && tableColumns.contains(columnRenameFrom)) {
                        MigrationCommand migrationCommand = renameColumn(entityMigrationMetadata, columnRenameFrom, column);
                        migrationCommands.add(migrationCommand);
                    } else {
                        MigrationCommand migrationCommand = addColumn(entityMigrationMetadata, column);
                        migrationCommands.add(migrationCommand);
                    }
                }
            }
        }
        return migrationCommands;
    }

    protected abstract MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column);

    protected abstract MigrationCommand addColumn(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata column);

    public String getQuoteSQLName(String val) {
        return EasyToSQLUtil.getQuoteSQLName(sqlKeyword, val);
    }

    public String getQuoteSQLName(String val1, String val2) {
        return EasyToSQLUtil.getQuoteSQLName(sqlKeyword, val1, val2);
    }
}