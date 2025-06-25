package com.easy.query.search.param;

/**
 * 参数字典获取器
 *
 * @author bkbits
 */
@FunctionalInterface
public interface EasySearchParamMapProvider {
    /**
     * @return 参数字典
     */
    ParamMap getParamMap();
}
