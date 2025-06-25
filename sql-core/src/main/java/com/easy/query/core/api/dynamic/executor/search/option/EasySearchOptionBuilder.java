package com.easy.query.core.api.dynamic.executor.search.option;

import java.util.Arrays;
import java.util.List;

/**
 * 搜索配置类，用于配置搜索执行器的行为和参数
 *
 * @author bkbits
 */
public class EasySearchOptionBuilder {
    private final List<String> excludes = Arrays.asList(
            "token",
            "page",
            "current",
            "pageSize"
    ); // 要排除的参数，在strict模式下尤为重要，传递非法的参数将当做错误处理
    private boolean strict = true; // 是否严格模式，默认true
    private boolean defaultEnabled = false; // 未标记@EasyCond的属性是否默认启用搜索，默认false
    private boolean indexEnabled = true; // 是否启用name-1、name-2的索引参数名，默认为true
    private boolean whereEnabled = true; // 是否启用where执行器，默认为true
    private boolean orderEnabled = true; // 是否启用order执行器，默认为true
    private String sortParam = "sort"; // 排序参数参数名
    private String groupSplitter = "|"; // 条件分组标志符，默认为"|"
    private String classSplitter = "."; // 条件类分隔符，默认为"."
    private String paramSplitter = "-"; // 参数分隔符，默认为"-"
    private String orderSplitter = ":"; // 参数分隔符，默认为":"

    public EasySearchOption build() {
        return new EasySearchOption(
                strict,
                defaultEnabled,
                indexEnabled,
                whereEnabled,
                orderEnabled,
                sortParam,
                groupSplitter,
                classSplitter,
                paramSplitter,
                orderSplitter,
                excludes
        );
    }

    public boolean isStrict() {
        return strict;
    }

    public EasySearchOptionBuilder setStrict(boolean strict) {
        this.strict = strict;
        return this;
    }

    public boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    public EasySearchOptionBuilder setDefaultEnabled(boolean defaultEnabled) {
        this.defaultEnabled = defaultEnabled;
        return this;
    }

    public boolean isIndexEnabled() {
        return indexEnabled;
    }

    public EasySearchOptionBuilder setIndexEnabled(boolean indexEnabled) {
        this.indexEnabled = indexEnabled;
        return this;
    }

    public boolean isWhereEnabled() {
        return whereEnabled;
    }

    public EasySearchOptionBuilder setWhereEnabled(boolean whereEnabled) {
        this.whereEnabled = whereEnabled;
        return this;
    }

    public boolean isOrderEnabled() {
        return orderEnabled;
    }

    public EasySearchOptionBuilder setOrderEnabled(boolean orderEnabled) {
        this.orderEnabled = orderEnabled;
        return this;
    }

    public String getSortParam() {
        return sortParam;
    }

    public EasySearchOptionBuilder setSortParam(String sortParam) {
        this.sortParam = sortParam;
        return this;
    }

    public String getGroupSplitter() {
        return groupSplitter;
    }

    public EasySearchOptionBuilder setGroupSplitter(String groupSplitter) {
        this.groupSplitter = groupSplitter;
        return this;
    }

    public String getClassSplitter() {
        return classSplitter;
    }

    public EasySearchOptionBuilder setClassSplitter(String classSplitter) {
        this.classSplitter = classSplitter;
        return this;
    }

    public String getParamSplitter() {
        return paramSplitter;
    }

    public EasySearchOptionBuilder setParamSplitter(String paramSplitter) {
        this.paramSplitter = paramSplitter;
        return this;
    }

    public String getOrderSplitter() {
        return orderSplitter;
    }

    public EasySearchOptionBuilder setOrderSplitter(String orderSplitter) {
        this.orderSplitter = orderSplitter;
        return this;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public EasySearchOptionBuilder setExcludes(List<String> exludes) {
        this.excludes.clear();
        this.excludes.addAll(exludes);
        return this;
    }

    public EasySearchOptionBuilder addExclude(String exclude) {
        if (!excludes.contains(exclude)) {
            excludes.add(exclude);
        }
        return this;
    }
}
