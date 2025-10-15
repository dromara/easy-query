package com.easy.query.core.basic.extension.track;

/**
 * @author xuejiaming
 * @FileName: TrackManager.java
 * @Description: 文件说明
 * create time 2023/3/19 13:51
 */
public interface TrackManager extends InvokeTryFinally {

    /**
     * 当前线程是否在追踪中
     *
     * @return
     */
    default boolean currentThreadTracking() {
        return getCurrentTrackContext() != null;
    }

    TrackContext getCurrentTrackContext();
    void setCurrentTrackContext(TrackContext trackContext);

}
