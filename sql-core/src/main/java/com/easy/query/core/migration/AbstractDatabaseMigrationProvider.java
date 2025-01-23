package com.easy.query.core.migration;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * create time 2025/1/18 20:09
 * 文件说明
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
            this.databaseName = EasyDatabaseUtil.getDatabaseName(dataSource);
        }
        return this.databaseName;
    }

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

    protected @NotNull ColumnDbTypeResult getColumnDbType0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return null;
    }

    @Override
    public String getColumnComment(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
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
        return getColumnComment0(entityMigrationMetadata, columnMetadata);
    }

    protected @Nullable String getColumnComment0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
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
        Field declaredField = entityMigrationMetadata.getFieldByName(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        if (annotation != null) {
            return annotation.nullable();
        }
        if(columnMetadata.getPropertyType().isPrimitive()){
            return false;
        }
        return isNullable0(entityMigrationMetadata, columnMetadata);
    }

    protected boolean isNullable0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return true;
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
        return columnExistInDb0(entityMigrationMetadata, columnMetadata);
    }

    protected boolean columnExistInDb0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return true;
    }

    @Override
    public @Nullable String getTableComment(EntityMigrationMetadata entityMigrationMetadata) {
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
        return getTableComment0(entityMigrationMetadata);
    }

    protected @Nullable String getTableComment0(EntityMigrationMetadata entityMigrationMetadata) {
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
        return getColumnRenameFrom0(entityMigrationMetadata, columnMetadata);
    }

    protected @Nullable String getColumnRenameFrom0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return null;
    }

    protected Set<String> getColumnNames(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();

        String columnTableName = EasyToSQLUtil.getSchemaTableName(sqlKeyword, entityMetadata, oldTable ? entityMetadata.getOldTableName() : entityMetadata.getTableName(), null, null);
        //比较差异
        return EasyDatabaseUtil.getColumns(dataSource, "select * from " + columnTableName + " where 1=2");
    }
}