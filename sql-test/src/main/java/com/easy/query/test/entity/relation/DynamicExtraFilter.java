package com.easy.query.test.entity.relation;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2025/2/19 15:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class DynamicExtraFilter {

    private static final ThreadLocal<Boolean> EXTRA_FILTER_ENABLE = ThreadLocal.withInitial(() -> true);

    public static boolean enable() {
        Boolean b = EXTRA_FILTER_ENABLE.get();
        if (b != null && !b) {
            return false;
        }
        return true;
    }

    public static void setEnable(boolean enable) {
        EXTRA_FILTER_ENABLE.set(enable);
    }

    public static void remove() {
        EXTRA_FILTER_ENABLE.remove();
    }

    public static ExtraFilterScope createScope(boolean enable){
        return new ExtraFilterScope(enable);
    }

    public static class ExtraFilterScope implements AutoCloseable{
        public ExtraFilterScope(boolean enable){
            DynamicExtraFilter.setEnable(enable);
        }

        @Override
        public void close() {
            DynamicExtraFilter.remove();
        }
    }
}
