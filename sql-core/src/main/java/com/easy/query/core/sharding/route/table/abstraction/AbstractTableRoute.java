package com.easy.query.core.sharding.route.table.abstraction;

import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.route.table.TableRoute;

/**
 * create time 2023/4/18 23:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractTableRoute implements TableRoute {

    private final EntityMetadataManager entityMetadataManager;

    public AbstractTableRoute(EntityMetadataManager entityMetadataManager){

        this.entityMetadataManager = entityMetadataManager;
    }

    public EntityMetadataManager getEntityMetadataManager() {
        return entityMetadataManager;
    }
}
