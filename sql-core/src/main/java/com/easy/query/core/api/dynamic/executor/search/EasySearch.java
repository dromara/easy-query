package com.easy.query.core.api.dynamic.executor.search;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.api.dynamic.executor.search.match.EasyTableMatch;
import com.easy.query.core.api.dynamic.executor.search.param.ParamMap;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;

import java.util.*;
import java.util.stream.Stream;

/**
 * 搜索类，用于构建和管理搜索条件
 *
 * @author bkbits
 */
public class EasySearch
        implements ObjectSort {
    private final ClassSearch search; //主搜索
    private Map<String, ClassSearch> namedSearch; //命名前缀的搜索
    private ParamMap paramMap; //搜索参数
    private List<String> defaultSort; //默认排序参数，当sort为空或不存在时使用

    private EasySearch(Class<?> search, ParamMap paramMap) {
        this.search = ClassSearch.of(search);
    }

    /**
     * 创建一个新的EasySearch实例
     *
     * @param searchClass 主搜索类
     * @return EasySearch实例
     */
    public static @NotNull EasySearch of(Class<?> searchClass) {
        return new EasySearch(searchClass, null);
    }

    /**
     * 创建一个新的EasySearch实例，带有参数映射
     *
     * @param searchClass 主搜索类
     * @param params      参数字典
     * @return EasySearch实例
     */
    public static @NotNull EasySearch of(Class<?> searchClass, ParamMap params) {
        return new EasySearch(searchClass, params);
    }

    /**
     * 添加一个命名的搜索类，并使用类匹配器
     *
     * @param name        搜索类的参数名称
     * @param searchClass 搜索类
     * @return this
     */
    public EasySearch table(String name, Class<?> searchClass) {
        if (namedSearch == null) {
            namedSearch = new HashMap<>();
        }
        namedSearch.put(name, ClassSearch.of(searchClass));
        return this;
    }

    /**
     * 添加一个命名的搜索类，并使用别名匹配器
     *
     * @param name        搜索类的参数名称
     * @param searchClass 搜索类
     * @return this
     */
    public EasySearch tableAlias(String name, Class<?> searchClass, String tableAlias) {
        if (namedSearch == null) {
            namedSearch = new HashMap<>();
        }
        namedSearch.put(name, ClassSearch.of(searchClass, tableAlias));
        return this;
    }

    /**
     * 添加一个命名的搜索类，并使用表索引匹配器
     *
     * @param name        搜索类的参数名称
     * @param searchClass 搜索类
     * @return this
     */
    public EasySearch tableIndex(String name, Class<?> searchClass, int tableIndex) {
        if (namedSearch == null) {
            namedSearch = new HashMap<>();
        }
        namedSearch.put(name, ClassSearch.of(searchClass, tableIndex));
        return this;
    }

    private void addParam(String key, @Nullable Object value) {
        if (value == null) {
            paramMap.addParam(key, null);
            return;
        }
        else if (value instanceof Iterable) {
            for (Object v : (Iterable<?>) value) {
                paramMap.addParam(key, v);
            }
        }
        else if (value.getClass().isArray()) {
            Stream.of((Object[]) value).forEach(v -> paramMap.addParam(key, v));
        }
        paramMap.addParam(key, value);
    }

    /**
     * 添加参数
     *
     * @param key    参数名
     * @param first  第一个参数值
     * @param values 参数值列表
     * @return this
     */
    public EasySearch param(String key, @Nullable Object first, @Nullable Object... values) {
        if (this.paramMap == null) {
            this.paramMap = ParamMap.create();
        }

        addParam(key, first);
        if (values != null) {
            Stream.of(values).forEach(v -> addParam(key, v));
        }
        return this;
    }

    /**
     * 添加多个搜索参数
     *
     * @param params 参数字典
     * @return this
     */
    public EasySearch paramMap(Map<String, Object> params) {
        if (this.paramMap == null) {
            this.paramMap = ParamMap.create();
        }
        this.paramMap.addParamMap(params);
        return this;
    }

    /**
     * 设置搜索参数
     *
     * @param params 参数映射
     * @return this
     */
    public EasySearch paramMap(ParamMap params) {
        this.paramMap = params;
        return this;
    }

    /**
     * @return 主搜索匹配器
     */
    public @NotNull EasyTableMatch getSearchMatcher() {
        return search.getTableMatch();
    }

    /**
     * @return 住搜索类
     */
    public @NotNull Class<?> getSearchClass() {
        return search.getSearchClass();
    }

    /**
     * @param name 参数类名
     * @return 搜索匹配器
     */
    public @Nullable EasyTableMatch getNamedSearchMatcher(String name) {
        ClassSearch matcher = getNamedSearch(name);
        if (matcher == null) {
            return null;
        }
        return matcher.getTableMatch();
    }

    /**
     * @param name 参数类名
     * @return 参数类
     */
    public @Nullable Class<?> getNamedSearchClass(String name) {
        ClassSearch matcher = getNamedSearch(name);
        if (matcher == null) {
            return null;
        }
        return matcher.getSearchClass();
    }

    /**
     * @param name 参数类名
     * @return 类搜索实例
     */
    private ClassSearch getNamedSearch(String name) {
        if (namedSearch == null) {
            return null;
        }
        return namedSearch.get(name);
    }

    /**
     * @return 参数字典
     */
    public @Nullable ParamMap getParamMap() {
        return paramMap;
    }

    /**
     * 添加默认排序
     *
     * @param sorts 排序参数
     * @return this
     */
    public @NotNull EasySearch addDefaultSort(String... sorts) {
        return addDefaultSort(Arrays.asList(sorts));
    }

    /**
     * 添加默认排序
     *
     * @param sorts 排序参数
     * @return this
     */
    public @NotNull EasySearch addDefaultSort(List<String> sorts) {
        if (defaultSort == null) {
            defaultSort = new ArrayList<>();
        }
        defaultSort.addAll(sorts);
        return this;
    }

    /**
     * 获取默认排序参数
     *
     * @return 默认排序参数列表
     */
    public @NotNull List<String> getDefaultSort() {
        if (defaultSort == null) {
            return Collections.emptyList();
        }
        return defaultSort;
    }

    @Override
    public void configure(ObjectSortBuilder builder) {
        // 空实现，实际处理在EasySearchWhereObjectQueryExecutor执行器中
    }
}
