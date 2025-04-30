package com.easy.query.core.migration;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.TableIndex;
import com.easy.query.core.annotation.TableIndexes;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/1/18 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationEntityParser implements MigrationEntityParser {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("TINYINT(1)", false));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("TINYINT(1)", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("FLOAT", 0f));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("FLOAT", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("DOUBLE", 0d));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("DOUBLE", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("SMALLINT(6)", 0));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("SMALLINT(6)", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("INT(11)", 0));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("INT(11)", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("BIGINT(20)", 0L));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("BIGINT(20)", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("TINYINT(3)", 0));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("TINYINT(3)", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("DECIMAL(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("DATETIME(3)", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("VARCHAR(255)", ""));
    }

    protected Map<Class<?>, ColumnDbTypeResult> getColumnTypeMap() {
        return columnTypeMap;
    }

    @Override
    public ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        Field declaredField = entityMigrationMetadata.getFieldByColumnMetadata(columnMetadata);
        Column annotation = declaredField.getAnnotation(Column.class);
        if (annotation != null) {
            String dbType = annotation.dbType();
            if (EasyStringUtil.isNotBlank(dbType)) {
                return new ColumnDbTypeResult(dbType, null);
            }
        }
        return getColumnTypeMap().get(columnMetadata.getPropertyType());
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
    public Boolean columnExistInDb(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {

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
    public List<TableIndexResult> getTableIndexes(EntityMigrationMetadata entityMigrationMetadata) {
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
        }
        else{
            if(EasyArrayUtil.isNotEmpty(tableIndexes.value())){
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
            return new TableIndexResult(indexName,tableIndex.unique(), entityFields);
        }
        return null;
    }
}
