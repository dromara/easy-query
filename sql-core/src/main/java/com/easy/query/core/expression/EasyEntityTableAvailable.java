package com.easy.query.core.expression;

import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/5/1 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyEntityTableAvailable implements EntityTableAvailable {
    private final int tableIndex;
    private final EntityMetadata entityMetadata;
    private final String alias;

    public EasyEntityTableAvailable(int tableIndex, EntityMetadata entityMetadata,String alias){

        this.tableIndex = tableIndex;
        this.entityMetadata = entityMetadata;
        this.alias = alias;
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
    public String getTableName() {
        return entityMetadata.getTableName();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getColumnName(String propertyName) {
        return entityMetadata.getColumnName(propertyName);
    }

    @Override
    public int getIndex() {
        return tableIndex;
    }
}
