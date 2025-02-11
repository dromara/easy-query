package com.easy.query.api.proxy.key;

import com.easy.query.core.util.EasyObjectUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class BigDecimalMapKey implements MapKey<BigDecimal> {
    private final String name;

    public BigDecimalMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<BigDecimal> getPropType() {
        return BigDecimal.class;
    }
}
