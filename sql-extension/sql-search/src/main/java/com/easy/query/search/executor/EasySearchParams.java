package com.easy.query.search.executor;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 条件参数
 *
 * @author bkbits
 */
class EasySearchParams {
    private Map<String, EasySearchParam> ungroupParams; //未分组的条件参数
    private Map<String, Map<String, EasySearchParam>> groupParams; //分组条件参数

    /**
     * 添加条件参数
     *
     * @param paramName  参数名
     * @param paramValue 参数值
     * @param index      参数排序值
     * @param supplier   CondParam创建器
     * @return this
     */
    @SuppressWarnings("unchecked")
    public EasySearchParams addCondParam(
            EasySearchParamName paramName,
            Object paramValue,
            int index,
            Supplier<EasySearchParam> supplier
    ) {
        Stream<Object> stream = null;
        if (paramValue != null) {
            if (paramValue.getClass().isArray()) {
                stream = Arrays.stream((Object[]) paramValue);
            }
            else if (paramValue instanceof List) {
                stream = ((List<Object>) paramValue).stream();
            }
        }
        if (stream == null) {
            stream = Stream.of(paramValue);
        }

        EasySearchParam condParam;
        if (paramName.getGroupName() != null && !paramName.getGroupName().isEmpty()) { //分组参数
            if (groupParams == null) {
                groupParams = new HashMap<>();
            }
            condParam = groupParams.computeIfAbsent(
                                           paramName.getGroupName(),
                                           g -> new LinkedHashMap<>()
                                   )
                                   .computeIfAbsent(
                                           paramName.getParamName(),
                                           k -> supplier.get()
                                   );
        }
        else { //未分组参数
            if (ungroupParams == null) {
                ungroupParams = new LinkedHashMap<>();
            }
            condParam = ungroupParams.computeIfAbsent(
                    paramName.getParamName(),
                    k -> supplier.get()
            );
        }
        stream.forEach(val -> condParam.addParam(val, index));

        return this;
    }

    /**
     * @return 未分组的条件参数
     */
    public @NotNull Map<String, EasySearchParam> getUngroupParams() {
        if (ungroupParams == null) {
            return Collections.emptyMap();
        }
        return ungroupParams;
    }

    /**
     * @return 分组条件参数
     */
    public @Nullable Map<String, EasySearchParam> getGroupParams(String groupName) {
        if (groupParams == null) {
            return null;
        }
        return groupParams.get(groupName);
    }
}
