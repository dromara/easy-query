package com.easy.query.core.migration;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ForeignKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.TableIndex;
import com.easy.query.core.annotation.TableIndexes;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * create time 2025/1/18 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationEntityParser implements MigrationEntityParser {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("TINYINT(1)", "0"));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("TINYINT(1)", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("FLOAT", "0"));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("FLOAT", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("DOUBLE", "0"));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("DOUBLE", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("SMALLINT(6)", "0"));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("SMALLINT(6)", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("INT(11)", "0"));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("INT(11)", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("BIGINT(20)", "0"));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("BIGINT(20)", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("TINYINT(3)", "0"));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("TINYINT(3)", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("DECIMAL(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("DATETIME(3)", null));
        columnTypeMap.put(LocalDate.class, new ColumnDbTypeResult("DATE", null));
        columnTypeMap.put(LocalTime.class, new ColumnDbTypeResult("TIME", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("VARCHAR(255)", null));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("VARCHAR(36)", null));
    }

    protected Map<Class<?>, ColumnDbTypeResult> getColumnTypeMap() {
        return columnTypeMap;
    }

    protected String replaceSqlTypeLength(String sqlType, int length, int scale) {
        if (sqlType == null || sqlType.isEmpty()) {
            return sqlType;
        }
        if (length < 0) {
            return sqlType;
        }

        // 查找第一个 '('
        int idx = sqlType.indexOf('(');
        String baseType;
        if (idx > 0) {
            baseType = sqlType.substring(0, idx).trim();
        } else {
            baseType = sqlType.trim();
        }

        // 拼接
        StringBuilder sb = new StringBuilder(baseType);
        if (length > 0) {
            sb.append('(').append(length);
            if (scale > 0) {
                sb.append(',').append(scale);
            }
            sb.append(')');
        }
        return sb.toString();
    }

    @Override
    @NotNull
    public ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        Field declaredField = entityMigrationMetadata.getFieldByColumnMetadata(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        String dbDefault = null;
        int length = -1;
        int scale = 0;
        if (annotation != null) {
            String dbType = annotation.dbType();
            length = annotation.length();
            scale = annotation.scale();
            dbDefault = annotation.dbDefault();
            if (EasyStringUtil.isNotBlank(dbType)) {
                return new ColumnDbTypeResult(dbType, dbDefault);
            }
        }
        ColumnDbTypeResult columnDbTypeResult = getColumnTypeMap().get(columnMetadata.getPropertyType());
        if (columnDbTypeResult == null) {
            throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(entityMigrationMetadata.getEntityMetadata().getEntityClass()) + "] field name:" + columnMetadata.getFieldName() + " not found column db type.");
        }
        if (EasyStringUtil.isNotBlank(dbDefault)) {
            return new ColumnDbTypeResult(replaceSqlTypeLength(columnDbTypeResult.columnType, length, scale), dbDefault);
        }
        return new ColumnDbTypeResult(replaceSqlTypeLength(columnDbTypeResult.columnType, length, scale), columnDbTypeResult.defValue);
    }

    @Override
    public String getColumnComment(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        Field declaredField = entityMigrationMetadata.getFieldByColumnMetadata(columnMetadata);
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
        if (columnMetadata.getPropertyType().isPrimitive()) {
            return false;
        }
        return columnMetadata.isNullable();
    }

    @Override
    public boolean columnExistInDb(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {

        Field declaredField = entityMigrationMetadata.getFieldByColumnMetadata(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        if (annotation != null) {
            return annotation.exist();
        }
        return true;
    }

    @Override
    public String getTableComment(EntityMigrationMetadata entityMigrationMetadata) {

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
        Field declaredField = entityMigrationMetadata.getFieldByColumnMetadata(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        if (annotation != null) {
            String renameFrom = annotation.renameFrom();
            if (EasyStringUtil.isNotBlank(renameFrom)) {
                return renameFrom;
            }
        }
        return null;
    }

    @Override
    public @NotNull List<TableIndexResult> getTableIndexes(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        ArrayList<TableIndexResult> tableIndexResults = new ArrayList<>();
        TableIndexes tableIndexes = EasyClassUtil.getAnnotation(entityMetadata.getEntityClass(), TableIndexes.class);
        if (tableIndexes == null) {
            TableIndex tableIndex = EasyClassUtil.getAnnotation(entityMetadata.getEntityClass(), TableIndex.class);
            if (tableIndex == null) {
                return EasyCollectionUtil.emptyList();
            }
            TableIndexResult tableIndexResult = parseTableIndex(tableIndex, entityMetadata.getTableName(), entityMetadata);
            if (tableIndexResult != null) {
                tableIndexResults.add(tableIndexResult);
            }
        } else {
            if (EasyArrayUtil.isNotEmpty(tableIndexes.value())) {
                for (TableIndex tableIndex : tableIndexes.value()) {
                    TableIndexResult tableIndexResult = parseTableIndex(tableIndex, entityMetadata.getTableName(), entityMetadata);
                    if (tableIndexResult != null) {
                        tableIndexResults.add(tableIndexResult);
                    }
                }
            }
        }

        return tableIndexResults;
    }

    private TableIndexResult parseTableIndex(TableIndex tableIndex, String tableName, EntityMetadata entityMetadata) {
        if (EasyArrayUtil.isNotEmpty(tableIndex.fields())) {

            String indexName = tableIndex.name();

            ArrayList<TableIndexResult.EntityField> entityFields = new ArrayList<>(tableIndex.fields().length);
            HashSet<String> descFields = new HashSet<>(Arrays.asList(tableIndex.descendingFields()));
            if (EasyStringUtil.isBlank(indexName)) {

                String[] columnNames = new String[tableIndex.fields().length];
                int index = 0;
                for (String field : tableIndex.fields()) {
                    String columnName = entityMetadata.getColumnNotNull(field).getName();
                    columnNames[index] = columnName;
                    entityFields.add(new TableIndexResult.EntityField(field, columnName, !descFields.contains(field)));
                    index++;
                }

                String columns = String.join("_", columnNames);
                boolean unique = tableIndex.unique();
                String indexPrefix = unique ? "u" : "";
                indexName = String.format("%s_id_%s_%sidx", tableName, columns, indexPrefix);
            } else {
                for (String field : tableIndex.fields()) {
                    String columnName = entityMetadata.getColumnNotNull(field).getName();
                    entityFields.add(new TableIndexResult.EntityField(field, columnName, !descFields.contains(field)));
                }
            }
            return new TableIndexResult(indexName, tableIndex.unique(), entityFields);
        }
        return null;
    }

    @Override
    @NotNull
    public List<TableForeignKeyResult> getTableForeignKeys(EntityMigrationMetadata entityMigrationMetadata, QueryRuntimeContext runtimeContext) {

        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        ArrayList<TableForeignKeyResult> tableForeignKeyResults = new ArrayList<>();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        for (NavigateMetadata navigateMetadata : entityMetadata.getNavigateMetadatas()) {
            Field field = entityMigrationMetadata.getFieldByName(navigateMetadata.getPropertyName());
            ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
            if (foreignKey != null) {
                String selfTable = entityMetadata.getTableName();
                String action = foreignKey.action();
                EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
                String targetTable = targetEntityMetadata.getTableName();
                String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
                String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
                String[] selfColumns = Arrays.stream(selfPropertiesOrPrimary).map(selfProp -> entityMetadata.getColumnNotNull(selfProp).getName()).toArray(String[]::new);
                String[] targetColumns = Arrays.stream(targetPropertiesOrPrimary).map(targetProp -> targetEntityMetadata.getColumnNotNull(targetProp).getName()).toArray(String[]::new);
                String name = String.format("%s_%s_%s_fk", selfTable, targetTable, String.join("_", targetColumns));
                tableForeignKeyResults.add(new TableForeignKeyResult(name, action, selfTable, targetTable, selfColumns, targetColumns));
            }
        }
        return tableForeignKeyResults;
    }
}
