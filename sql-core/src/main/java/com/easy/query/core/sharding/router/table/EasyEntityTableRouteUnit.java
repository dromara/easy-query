package com.easy.query.core.sharding.router.table;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/25 14:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityTableRouteUnit extends BaseTableRouteUnit implements EntityTableRouteUnit {
    private final Object entity;

    public EasyEntityTableRouteUnit(TableRouteUnit tableRouteUnit, Object entity) {
        this(tableRouteUnit.getDataSourceName(), tableRouteUnit.getActualTableName(), tableRouteUnit.getTable(),entity);
    }
    public EasyEntityTableRouteUnit(String dataSource, String actualTableName, TableAvailable table, Object entity) {
        super(dataSource, actualTableName, table);
        this.entity = entity;
    }

    @Override
    public Object getEntity() {
        return entity;
    }
}
