package com.easy.query.core.sharding.route.table;

/**
 * create time 2023/4/25 14:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyInsertTableRouteUnit extends BaseTableRouteUnit implements InsertTableRouteUnit{
    private final Object entity;

    public EasyInsertTableRouteUnit(TableRouteUnit tableRouteUnit, Object entity) {
        this(tableRouteUnit.getDataSource(), tableRouteUnit.getLogicTableName(), tableRouteUnit.getActualTableName(), tableRouteUnit.getEntityClass(),entity);
    }
    public EasyInsertTableRouteUnit(String dataSource, String logicTableName, String actualTableName, Class<?> entityClass, Object entity) {
        super(dataSource, logicTableName, actualTableName, entityClass);
        this.entity = entity;
    }

    @Override
    public Object getEntity() {
        return entity;
    }
}
