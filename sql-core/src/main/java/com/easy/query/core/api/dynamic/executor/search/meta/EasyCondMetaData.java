package com.easy.query.core.api.dynamic.executor.search.meta;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.annotation.EasyCond;
import com.easy.query.core.api.dynamic.executor.search.EasySortType;
import com.easy.query.core.api.dynamic.executor.search.exception.EasySearchSortException;
import com.easy.query.core.api.dynamic.executor.search.exception.EasySearchWhereException;
import com.easy.query.core.api.dynamic.executor.search.match.EasyTableMatch;
import com.easy.query.core.api.dynamic.executor.search.op.*;
import com.easy.query.core.metadata.ColumnMetadata;

import java.lang.reflect.Field;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 搜索条件元数据
 *
 * @author bkbits
 */
public class EasyCondMetaData {
    private static final List<EasySortType> DEFAULT_SORT_ONLY =
            Collections.unmodifiableList(Arrays.asList(
                    EasySortType.Asc,
                    EasySortType.Desc
            ));

    private final ColumnMetadata columnMetadata;
    private final EasyTableMatch tableMatch;
    private final String name;
    private final String propertyName;
    private final Class<? extends Op> cond;
    private final List<Class<? extends Op>> condOnly;
    private final EasySortType sort;
    private final List<EasySortType> sortOnly;

    public EasyCondMetaData(
            @NotNull ColumnMetadata columnMetadata,
            @Nullable EasyTableMatch tableMatch,
            @NotNull String name,
            @NotNull String propertyName,
            @NotNull Class<? extends Op> cond,
            @NotNull List<Class<? extends Op>> condOnly,
            @NotNull EasySortType sort,
            @NotNull List<EasySortType> sortOnly
    ) {
        this.columnMetadata = columnMetadata;
        this.cond = cond;
        this.tableMatch = tableMatch;
        this.name = name;
        this.propertyName = propertyName;
        this.condOnly = condOnly;
        this.sort = sort;
        this.sortOnly = sortOnly;
    }

    public static EasyCondMetaData of(
            ColumnMetadata columnMetadata,
            Field field,
            EasyTableMatch defaultTableMatch
    ) throws Exception {
        EasyCond cond = field.getDeclaredAnnotation(EasyCond.class);
        EasyTableMatch tableMatch = defaultTableMatch;
        String name = null;
        String propertyName = null;
        Class<? extends Op> defaultCond = null;
        List<Class<? extends Op>> condOnly = Collections.emptyList();
        EasySortType sort = EasySortType.None;
        List<EasySortType> sortOnly = DEFAULT_SORT_ONLY;

        if (cond != null) { //明确的注解
            EasyTableMatch condMatch = EasyTableMatch.of(
                    cond.table(),
                    cond.tableAlias(),
                    cond.tableIndex()
            );
            if (condMatch != null) {
                tableMatch = condMatch;
            }

            name = cond.name().trim();
            propertyName = cond.property().trim();

            if (!cond.cond().equals(Op.class)) {
                defaultCond = cond.cond();
            }

            if (cond.condOnly().length != 0) {
                condOnly = Arrays.asList(cond.condOnly());
            }

            sort = cond.sort();

            if (cond.sortOnly().length == 0) {
                sortOnly = Collections.emptyList();
            }
            else {
                sortOnly = Arrays.asList(cond.sortOnly());
            }
        }

        if (name == null || name.isEmpty()) { //未设置名字，使用字段名作为参数名
            name = field.getName();
        }

        if (propertyName == null || propertyName.isEmpty()) {
            propertyName = field.getName();
        }

        if (defaultCond == null) { //未设置默认查询条件，根据类型设定条件
            Class<?> fieldType = field.getType();
            if (fieldType.equals(String.class)) {
                defaultCond = Like.class;
            }
            else if (Date.class.isAssignableFrom(fieldType) || Temporal.class.isAssignableFrom(
                    fieldType)) {
                defaultCond = RangeClosed.class;
            }
            else if (List.class.isAssignableFrom(fieldType)) {
                defaultCond = In.class;
            }
            else {
                defaultCond = Equals.class;
            }
        }
        return new EasyCondMetaData(
                columnMetadata,
                tableMatch,
                name,
                propertyName,
                defaultCond,
                condOnly,
                sort,
                sortOnly
        );
    }

    public @NotNull ColumnMetadata getColumn() {
        return columnMetadata;
    }

    public @NotNull Class<? extends Op> getCond() {
        return cond;
    }

    public @Nullable EasyTableMatch getTableMatch() {
        return tableMatch;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getPropertyName() {
        return propertyName;
    }

    public @NotNull List<Class<? extends Op>> getCondOnly() {
        return condOnly;
    }

    public @NotNull EasySortType getSort() {
        return sort;
    }

    public boolean isSortEnabled(EasySortType sortType) {
        if (sortType == EasySortType.None) {
            return false;
        }
        return sortOnly.contains(sortType);
    }

    public void checkSortEnabled(EasySortType sortType) {
        if (!isSortEnabled(sortType)) {
            throw new EasySearchSortException("排序类型不在支持范围内");
        }
    }

    public boolean isCondEnabled(Class<? extends Op> condClass) {
        if (condOnly.isEmpty()) {
            return true;
        }
        return condOnly.contains(condClass);
    }

    public void checkCondEnabled(Class<? extends Op> condClass) {
        if (!isCondEnabled(condClass)) {
            throw new EasySearchWhereException("查询条件不在支持的范围内");
        }
    }

    public @NotNull List<EasySortType> getSortOnly() {
        return sortOnly;
    }
}
