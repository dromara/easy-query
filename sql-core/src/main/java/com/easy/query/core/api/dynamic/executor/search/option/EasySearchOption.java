package com.easy.query.core.api.dynamic.executor.search.option;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 搜索配置类，用于配置搜索执行器的行为和参数
 *
 * @author bkbits
 */
public class EasySearchOption {
    private final boolean strict; // 是否严格模式，默认false
    private final boolean defaultEnabled; // 未标记@EasyCond的属性是否默认启用搜索，默认false
    private final boolean indexEnabled; // 是否启用name-1、name-2的索引参数名，默认为true
    private final boolean whereEnabled; // 是否启用where执行器，默认为true
    private final boolean orderEnabled; // 是否启用order执行器，默认为true
    private final String sortParam; //排序参数参数名，默认为sort
    private final String groupSplitter; // 条件分组标志符，默认为"|"
    private final String classSplitter; // 条件类分隔符，默认为"."
    private final String paramSplitter; // 参数分隔符，默认为"-"
    private final String orderSplitter; // 参数分隔符，默认为":"
    private final Set<String> excludes;  // 要排除的参数，在strict模式下尤为重要，传递非法的参数将当做错误处理

    public EasySearchOption(
            boolean strict, boolean defaultEnabled, boolean indexEnabled,
            boolean whereEnabled, boolean orderEnabled, String sortParam, String groupSplitter,
            String classSplitter,
            String paramSplitter, String orderSplitter, Collection<String> excludes
    ) {
        this.strict = strict;
        this.defaultEnabled = defaultEnabled;
        this.indexEnabled = indexEnabled;
        this.whereEnabled = whereEnabled;
        this.orderEnabled = orderEnabled;
        this.sortParam = sortParam;
        this.groupSplitter = groupSplitter;
        this.classSplitter = classSplitter;
        this.paramSplitter = paramSplitter;
        this.orderSplitter = orderSplitter;
        this.excludes = Collections.unmodifiableSet(new HashSet<>(excludes));
    }

    public boolean isStrict() {
        return strict;
    }

    public boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    public boolean isIndexEnabled() {
        return indexEnabled;
    }

    public boolean isWhereEnabled() {
        return whereEnabled;
    }

    public boolean isOrderEnabled() {
        return orderEnabled;
    }

    public String getGroupSplitter() {
        return groupSplitter;
    }

    public String getClassSplitter() {
        return classSplitter;
    }

    public String getParamSplitter() {
        return paramSplitter;
    }

    public String getOrderSplitter() {
        return orderSplitter;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    public String getSortParam() {
        return sortParam;
    }
}
