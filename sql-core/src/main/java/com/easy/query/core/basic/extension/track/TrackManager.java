package com.easy.query.core.basic.extension.track;

/**
 * @FileName: TrackManager.java
 * @Description: 文件说明
 * @Date: 2023/3/19 13:51
 * @author xuejiaming
 */
public interface TrackManager extends InvokeTryFinally {

    /**
     * 当前线程是否在追踪中
     * @return
     */
   default boolean currentThreadTracking(){
       return getCurrentTrackContext()!=null;
   }
    TrackContext getCurrentTrackContext();


}
