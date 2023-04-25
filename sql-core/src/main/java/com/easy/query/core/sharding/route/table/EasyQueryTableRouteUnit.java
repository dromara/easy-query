package com.easy.query.core.sharding.route.table;

/**
 * create time 2023/4/25 14:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryTableRouteUnit extends BaseTableRouteUnit implements QueryTableRouteUnit{
    public EasyQueryTableRouteUnit(String dataSource, String logicTableName, String actualTableName, Class<?> entityClass) {
        super(dataSource, logicTableName, actualTableName, entityClass);
    }
}
