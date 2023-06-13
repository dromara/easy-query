package com.easy.query.core.sharding.router.descriptor;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/5/18 16:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityRouteDescriptorImpl implements EntityRouteDescriptor{
    private final TableAvailable table;
    private final Object entity;

    public EntityRouteDescriptorImpl(TableAvailable table, Object entity){
        this.table = table;

        this.entity = entity;
    }
    @Override
    public Object getEntity() {
        return entity;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
