package com.easy.query.core.metadata;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.func.def.enums.OrderByModeEnum;

/**
 * create time 2024/10/18 15:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateOrderProp {
    private final String property;
    private final boolean asc;
    private final OrderByModeEnum mode;

    public NavigateOrderProp(@NotNull String property, boolean asc, @Nullable OrderByModeEnum mode) {
        this.property = property;
        this.asc = asc;
        this.mode = mode;
    }

    @NotNull
    public String getProperty() {
        return property;
    }

    public boolean isAsc() {
        return asc;
    }

    @Nullable
    public OrderByModeEnum getMode() {
        return mode;
    }
}
