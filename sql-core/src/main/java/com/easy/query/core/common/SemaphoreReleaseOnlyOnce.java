package com.easy.query.core.common;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * create time 2023/5/25 13:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SemaphoreReleaseOnlyOnce {
    private final AtomicBoolean released = new AtomicBoolean(false);
    private final int permits;
    private final Semaphore semaphore;

    public SemaphoreReleaseOnlyOnce(int permits,Semaphore semaphore) {
        this.permits = permits;
        this.semaphore = semaphore;
    }

    public void release() {
        if (this.semaphore != null) {
            if (this.released.compareAndSet(false, true)) {
                this.semaphore.release(permits);
            }
        }
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
