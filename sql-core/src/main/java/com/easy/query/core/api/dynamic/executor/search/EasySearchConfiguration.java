package com.easy.query.core.api.dynamic.executor.search;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.api.dynamic.executor.search.op.Op;
import com.easy.query.core.api.dynamic.executor.search.option.EasySearchOption;
import com.easy.query.core.api.dynamic.executor.search.param.EasySearchParamMapProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * EasySearch配置类，包含搜索选项和操作符字典
 * 用于存储EasySearch的配置选项和操作符映射
 * 提供获取操作符实例的方法
 *
 * @author bkbits
 */
public class EasySearchConfiguration {
    private final EasySearchOption easySearchOption; //配置
    private final Map<String, Op> opMap; //运算符字典
    private final Map<Class<? extends Op>, Op> opClassMap; //运算符实例字典
    private final EasySearchParamMapProvider paramMapProvider; //参数获取器

    public EasySearchConfiguration(
            @NotNull EasySearchOption option,
            @NotNull Map<String, Op> ops,
            @Nullable EasySearchParamMapProvider paramMapProvider
    ) {
        this.easySearchOption = option;
        this.opMap = Collections.unmodifiableMap(ops);
        Map<Class<? extends Op>, Op> tempMap = new HashMap<>();
        this.opMap.forEach((key, val) -> {
            tempMap.put(val.getClass(), val);
        });
        this.opClassMap = Collections.unmodifiableMap(tempMap);
        this.paramMapProvider = paramMapProvider;
    }

    /**
     * @return 是否为严格模式
     */
    public boolean isStrict() {
        return easySearchOption.isStrict();
    }

    /**
     * @return 配置
     */
    public @NotNull EasySearchOption getOption() {
        return easySearchOption;
    }

    /**
     * @return 所有操作符
     */
    public @NotNull Map<String, Op> getAllOp() {
        return opMap;
    }

    /**
     * @param opName 操作符名称
     * @return 操作符实例
     */
    public @Nullable Op getOp(@NotNull String opName) {
        return opMap.get(opName);
    }

    /**
     * @param opClass 操作符类
     * @return 操作符实例
     */
    public @Nullable Op getOp(@NotNull Class<? extends Op> opClass) {
        return opClassMap.get(opClass);
    }

    /**
     * @return 参数提供器
     */
    public @Nullable EasySearchParamMapProvider getParamMapProvider() {
        return paramMapProvider;
    }
}
