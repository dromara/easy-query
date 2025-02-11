package com.easy.query.api.proxy.key;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.util.EasyObjectUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * create time 2024/3/2 19:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapKey<TPropType> {
    /**
     * 定义的key的name
     *
     * @return
     */
    @NotNull
    String getName();

    /**
     * 返回的类型
     *
     * @return
     */
    Class<TPropType> getPropType();

    default <TValue> @Nullable TPropType getValueOrNull(Map<String, TValue> map) {
        TValue value = map.get(getName());
        if (value == null) {
            return null;
        }
        return EasyObjectUtil.typeCastNullable(value);
    }
}
