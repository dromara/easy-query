package com.easy.query.core.basic.plugin.track;

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

   default void addTracking(Object entity){
       addTracking(entity,false);
   }
    void addTracking(Object entity,boolean isQuery);
    void release();
}
