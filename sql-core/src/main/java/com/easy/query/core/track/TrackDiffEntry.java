package com.easy.query.core.track;

/**
 * @FileName: TrackDiffEntry.java
 * @Description: 文件说明
 * @Date: 2023/3/20 10:06
 * @author xuejiaming
 */
public class TrackDiffEntry {
    private final Object original;
    private final Object current;

    public TrackDiffEntry(Object original, Object current){

        this.original = original;
        this.current = current;
    }

    public Object getOriginal() {
        return original;
    }

    public Object getCurrent() {
        return current;
    }
}
