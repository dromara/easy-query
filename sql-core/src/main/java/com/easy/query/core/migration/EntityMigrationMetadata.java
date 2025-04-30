package com.easy.query.core.migration;

import com.easy.query.core.annotation.TableIndex;
import com.easy.query.core.annotation.TableIndexes;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/1/18 16:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityMigrationMetadata {
    private final EntityMetadata entityMetadata;
    private final Map<String, Field> allFieldsMap;

    public EntityMigrationMetadata(EntityMetadata entityMetadata) {
        this.entityMetadata = entityMetadata;
        this.allFieldsMap = EasyClassUtil.getAllFieldsMap(entityMetadata.getEntityClass());
    }


    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public Field getFieldByColumnMetadata(ColumnMetadata columnMetadata) {
        return getFieldByName(columnMetadata.getFieldName());
    }
    public Field getFieldByName(String fieldName) {
        return allFieldsMap.get(fieldName);
    }

    public Map<String, Field> getAllFieldsMap() {
        return allFieldsMap;
    }
}
