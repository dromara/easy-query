package com.easy.query.core.common;

/**
 * create time 2024/10/27 08:59
 * 文件说明
 *
 * @author xuejiaming
 */
public final class OffsetLimitEntry {
    public static final OffsetLimitEntry EMPTY = new OffsetLimitEntry(0,0);
    public final long offset;
    public final long limit;

    public OffsetLimitEntry(long offset, long limit){
        this.offset = offset;
        this.limit = limit;
    }
}
