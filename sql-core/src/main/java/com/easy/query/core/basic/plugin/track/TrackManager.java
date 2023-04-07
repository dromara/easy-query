package com.easy.query.core.basic.plugin.track;

/**
 * @FileName: TrackManager.java
 * @Description: 文件说明
 * @Date: 2023/3/19 13:51
 * @author xuejiaming
 */
public interface TrackManager {
    /**
     * 当前上下文开启追踪，需要和{@link #release()}成对使用
     */
    void begin();

    /**
     * 当前线程是否在追踪中
     * @return
     */
   default boolean currentThreadTracking(){
       return getCurrentTrackContext()!=null;
   }
    TrackContext getCurrentTrackContext();

    /**
     * 当前上下文清空，需要和{@link #begin()}成对使用
     */
    void release();

}
