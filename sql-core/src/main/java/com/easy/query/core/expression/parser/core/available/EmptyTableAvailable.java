package com.easy.query.core.expression.parser.core.available;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2024/6/8 20:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyTableAvailable implements TableAvailable{
    public static final EmptyTableAvailable DEFAULT=new EmptyTableAvailable();
    @Override
    public Class<?> getEntityClass() {
        return null;
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return null;
    }

    @Override
    public boolean hasSchema() {
        return false;
    }

    @Override
    public String getSchema() {
        return "";
    }

    @Override
    public String getTableName() {
        return "";
    }

    @Override
    public String getColumnName(String propertyName) {
        return "";
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Override
    public void asAlias(String alias) {

    }

    @Override
    public String getAlias() {
        return "";
    }
}
