package com.easy.query.core.sharding.common;

/**
 * create time 2023/4/20 10:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class RouteMapper {
    private final Class<?> entityClass;
    private final String logicName;
    private final String actualName;

    public RouteMapper(Class<?> entityClass,String logicName, String actualName){
        this.entityClass = entityClass;

        this.logicName = logicName;
        this.actualName = actualName;
    }

    public String getLogicName() {
        return logicName;
    }

    public String getActualName() {
        return actualName;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

}
