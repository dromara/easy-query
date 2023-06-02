package com.easy.query.core.basic.extension.track;

import com.easy.query.core.exception.EasyQueryException;

/**
 * @FileName: TrackContext.java
 * @Description: 文件说明
 * @Date: 2023/3/18 21:21
 * @author xuejiaming
 */
public interface TrackContext {
    void begin();
    boolean isTrack(Object entity);

    /**
     * 没有或者不符合返回null
     * @param entity
     * @return
     */
    EntityState getTrackEntityState(Object entity);

    /**
     * 添加当前对象到追踪上下文,如果当前对象已经被追踪且追踪对象和添加对象不是同一个对象那么就会抛错
     * @param entity
     * @throws EasyQueryException 追踪对象为null,追踪对象无法获取key,追踪对象已经被追踪且不是同一个对象,追踪对象无法获取表名
     * @return true:添加成功,false:已经存在相同对象
     */
   default boolean addTracking(Object entity){
       return addTracking(entity,false);
   }

    /**
     * 查询的情况下添加追踪如果当前添加的对象已经被追踪,且追踪对象和当前对象不是同一个对象,
     * 那么当前添加的对象将不会被添加到上下文中,接口一般用于查询时候返回对象
     * @param entity
     * @param isQuery
     * @throws EasyQueryException 追踪对象为null,追踪对象无法获取key,追踪对象已经被追踪且不是同一个对象,追踪对象无法获取表名
     * @return true:添加成功,false:已经存在相同对象
     */
    boolean addTracking(Object entity,boolean isQuery);
    void release();
}
