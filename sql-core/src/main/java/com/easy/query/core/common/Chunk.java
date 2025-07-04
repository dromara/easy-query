package com.easy.query.core.common;

/**
 * create time 2025/7/4 21:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class Chunk<T> {
    private final T values;
    private boolean breakChunk;
    private long maxFetchSize;
    private final long fetchSize;

    public Chunk(T values, long fetchSize) {
        this.values = values;
        this.fetchSize = fetchSize;
        this.maxFetchSize = 100000;
    }

    public void breakChunk() {
        this.breakChunk = true;
    }

    public T getValues() {
        return values;
    }

    public boolean isBreakChunk() {
        return breakChunk;
    }

    /**
     * 终于拉取数量
     * @return
     */
    public long getMaxFetchSize() {
        return maxFetchSize;
    }

    public void setMaxFetchSize(long maxFetchSize) {
        this.maxFetchSize = maxFetchSize;
    }

    /**
     * 已拉取数量包含当前values
     * @return
     */
    public long getFetchSize() {
        return fetchSize;
    }

    public Offset offset(int offset) {
        return new Offset(offset);
    }

    public static class Offset {
        public final int offset;

        public Offset(int offset) {
            this.offset = offset;
        }
    }
}
