package com.easy.query.core.util;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.function.Function;

/**
 * create time 2025/1/11 14:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyToSQLUtil {

    public static String getTableName(SQLKeyword sqlKeyword, EntityMetadata entityMetadata, String entityTableName, Function<String, String> schemaAs, Function<String, String> tableNameAs) {
        String tableName = sqlKeyword.getQuoteName(doGetTableName(entityMetadata, entityTableName, tableNameAs));
        String schema = doGetSchema(entityMetadata, schemaAs);
        if (EasyStringUtil.isNotBlank(schema)) {
            if (schema.contains(".")) {
                return String.join(".", EasyCollectionUtil.select(schema.split("\\."), (s, i) -> sqlKeyword.getQuoteName(s))) + "." + tableName;
            } else {
                return sqlKeyword.getQuoteName(schema) + "." + tableName;
            }
        }
        return tableName;
    }

    private static String doGetSchema(EntityMetadata entityMetadata, Function<String, String> schemaAs) {
        String schema = entityMetadata.getSchemaOrNull();
        if (schema != null || schemaAs != null) {
            if (schemaAs != null) {
                return schemaAs.apply(schema);
            }
            return schema;
        }
        return null;
    }

    private static String doGetTableName(EntityMetadata entityMetadata, String tableName, Function<String, String> tableNameAs) {
        if (tableNameAs != null) {
            String applyTableName = tableNameAs.apply(tableName);
            return checkTableName(applyTableName, entityMetadata.getEntityClass());
        }
        return checkTableName(tableName, entityMetadata.getEntityClass());
    }

    private static String checkTableName(String tableName, Class<?> entityClass) {

        if (tableName == null) {
            throw new EasyQueryException("table " + EasyClassUtil.getSimpleName(entityClass) + " cant found mapping table name");
        }
        return tableName;
    }
}
