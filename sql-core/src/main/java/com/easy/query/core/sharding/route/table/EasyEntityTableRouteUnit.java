package com.easy.query.core.sharding.route.table;

/**
 * create time 2023/4/25 14:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityTableRouteUnit extends BaseTableRouteUnit implements EntityTableRouteUnit {
    private final Object entity;

    public EasyEntityTableRouteUnit(TableRouteUnit tableRouteUnit, Object entity) {
        this(tableRouteUnit.getDataSource(), tableRouteUnit.getLogicTableName(), tableRouteUnit.getActualTableName(), tableRouteUnit.getEntityClass(),entity);
    }
    public EasyEntityTableRouteUnit(String dataSource, String logicTableName, String actualTableName, Class<?> entityClass, Object entity) {
        super(dataSource, logicTableName, actualTableName, entityClass,0);
        this.entity = entity;
    }

    @Override
    public Object getEntity() {
        return entity;
    }
}
