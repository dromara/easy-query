package com.easy.query.core.migration.commands;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.MigrationCommand;

/**
 * create time 2025/1/14 14:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationCommand implements MigrationCommand {
    private final EntityMetadata entityMetadata;
    private final String sql;

    public DefaultMigrationCommand(EntityMetadata entityMetadata, String sql){
        this.entityMetadata = entityMetadata;
        this.sql = sql;
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public String toSQL() {
        return sql;
    }

    @Override
    public void execute() {

    }
}
