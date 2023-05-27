package com.easy.query.core.expression;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/5/1 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EntityTableAvailable implements TableAvailable {
    private final int tableIndex;
    private final EntityMetadata entityMetadata;
    private final boolean hasSchema;
    private final boolean isAnonymous;
    private String alias;

    public EntityTableAvailable(int tableIndex, EntityMetadata entityMetadata,boolean isAnonymous){

        this.tableIndex = tableIndex;
        this.entityMetadata = entityMetadata;
        this.hasSchema= EasyStringUtil.isNotBlank(entityMetadata.getSchemaOrNull());
        this.isAnonymous = isAnonymous;
    }
    @Override
    public Class<?> getEntityClass() {
        return entityMetadata.getEntityClass();
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public boolean hasSchema() {
        return hasSchema;
    }

    @Override
    public String getTableName() {
        return entityMetadata.getTableName();
    }

    @Override
    public String getSchema() {
        return entityMetadata.getSchemaOrNull();
    }

    @Override
    public String getColumnName(String propertyName) {
        return entityMetadata.getColumnName(propertyName);
    }

    @Override
    public boolean isAnonymous() {
        return isAnonymous;
    }

    @Override
    public void asAlias(String alias) {
        this.alias=alias;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public int getIndex() {
        return tableIndex;
    }
}
