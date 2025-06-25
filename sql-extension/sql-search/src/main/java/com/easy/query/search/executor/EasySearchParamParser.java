package com.easy.query.search.executor;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.search.EasySearch;
import com.easy.query.search.EasySearchConfiguration;
import com.easy.query.search.EasySortType;
import com.easy.query.search.exception.EasySearchInternalException;
import com.easy.query.search.exception.EasySearchStatusException;
import com.easy.query.search.match.EasyTableMatch;
import com.easy.query.search.meta.EasyCondMetaData;
import com.easy.query.search.meta.EasySearchMetaData;
import com.easy.query.search.meta.EasySearchMetaDataManager;
import com.easy.query.search.option.EasySearchOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 参数解析器，用于解析搜索参数和排序参数
 * 支持解析格式为 "groupName|className.paramName" 或 "className.paramName" 或 "paramName"
 * 以及排序参数格式 "className.paramName" 或 "paramName" 或 "className.paramName:orderType"
 *
 * @author bkbits
 */
public class EasySearchParamParser {
    private final EasySearchMetaDataManager metaDataManager;
    private final EasySearchOption option;

    public EasySearchParamParser(
            EasySearchMetaDataManager metaDataManager,
            EasySearchConfiguration configuration
    ) {
        if (metaDataManager == null) {
            throw new EasySearchInternalException("EasySearchMetaDataManager is null");
        }
        if (configuration == null) {
            throw new EasySearchInternalException("EasySearchConfiguration is null");
        }
        this.metaDataManager = metaDataManager;
        this.option = configuration.getOption();
    }

    /**
     * @param paramName 参数名
     * @return 符号参数名
     */
    public String getOpParamName(@NotNull String paramName) {
        paramName = paramName.trim();
        Splitted splitted = splitLast(paramName, option.getParamSplitter());
        if (splitted != null) {
            paramName = splitted.getLeft();
        }
        return paramName + option.getParamSplitter() + "op";
    }

    /**
     * 解析参数名，默认格式为 "[groupName|][className.]paramName[-index]"
     *
     * @param easySearch 搜索对象
     * @param paramName  参数名字符串
     * @return 解析后的 ParamName 对象
     * @throws EasySearchStatusException 如果参数名格式不正确
     */
    public @NotNull EasySearchParamName parseParamName(
            @NotNull EasySearch easySearch,
            @NotNull String paramName
    ) {
        String name = paramName.trim();
        //解析分组名
        Splitted splitted = splitFirst(name, option.getGroupSplitter());
        String groupName = null;
        if (splitted != null) {
            groupName = splitted.getLeft();
            name = splitted.getRight();
        }

        //解析类名
        splitted = splitFirst(name, option.getClassSplitter());
        String className = null;
        if (splitted != null) {
            className = splitted.getLeft();
            name = splitted.getRight();
        }

        //解析参数序号
        Integer index = null;
        splitted = splitLast(name, option.getParamSplitter());
        if (splitted != null) {
            name = splitted.getLeft();
            String indexStr = splitted.getRight();
            if (!isNumber(indexStr)) { //后缀指示符不是一个数字
                return null;
            }
            //后缀是数字，作为索引处理
            index = Integer.parseInt(indexStr.trim());
        }

        EasyTableMatch tableMatch;
        if (className == null) {
            tableMatch = easySearch.getSearchMatcher();
        }
        else {
            tableMatch = easySearch.getNamedSearchMatcher(className);
        }

        // 查询元数据
        EasyCondMetaData metaData = getCondMetaData(easySearch, className, name);
        if (metaData == null) { //找不到对应元数据，返回空
            return null;
        }

        return new EasySearchParamName(groupName, metaData, tableMatch, name, index);
    }

    /**
     * 解析排序参数，格式为 "[className.]paramName[:orderType]"
     *
     * @param easySearch 搜索对象
     * @param sortStr    排序参数字符串
     * @return 解析后的 SortParam 对象
     * @throws EasySearchStatusException 如果排序参数格式不正确
     */
    public @NotNull EasySearchSortParam parseSort(
            @NotNull EasySearch easySearch,
            @NotNull String sortStr
    ) {
        sortStr = sortStr.trim();
        //解析类名
        Splitted splitted = splitFirst(sortStr, option.getClassSplitter());
        String className = null;
        if (splitted != null) {
            className = splitted.getLeft();
            sortStr = splitted.getRight();
        }

        //解析排序类型
        EasySortType sortType = null;
        splitted = splitLast(sortStr, option.getOrderSplitter());
        if (splitted != null) {
            sortStr = splitted.getLeft();
            sortType = EasySortType.of(splitted.getRight());
        }


        EasyTableMatch tableMatch;
        if (className == null) {
            tableMatch = easySearch.getSearchMatcher();
        }
        else {
            tableMatch = easySearch.getNamedSearchMatcher(className);
        }

        // 查询元数据
        EasyCondMetaData metaData = getCondMetaData(easySearch, className, sortStr);
        if (metaData == null) { //找不到对应元数据，返回空
            return null;
        }

        return new EasySearchSortParam(metaData, sortStr, tableMatch, sortType);
    }

    /**
     * 将字符串按指定分隔符分割成列表
     *
     * @param str      字符串
     * @param splitter 分隔符
     * @return 分割后的字符串列表，如果字符串为空则返回空列表
     */
    private @NotNull List<String> split(@NotNull String str, @NotNull String splitter) {
        if (str.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> strs = new ArrayList<>();
        int index = 0;
        while (true) {
            int nextIndex = str.indexOf(splitter, index);
            if (nextIndex == -1) {
                strs.add(str.substring(index).trim());
                break;
            }
            strs.add(str.substring(index, nextIndex).trim());
            index = nextIndex + splitter.length();
        }

        return strs;
    }

    /**
     * 获取字符串第一个分隔符后的内容
     *
     * @param str      字符串
     * @param splitter 分隔符
     * @return 分隔符前的内容，如果没有找到分隔符或字符串为空则返回null
     */
    private @Nullable Splitted splitFirst(@NotNull String str, String splitter) {
        if (str.isEmpty()) {
            return null;
        }
        int index = str.indexOf(splitter);
        if (index == -1) {
            return null;
        }

        return new Splitted(
                str.substring(0, index).trim(),
                str.substring(index + splitter.length()).trim()
        );
    }

    /**
     * 获取字符串最后一个分隔符后的内容
     *
     * @param str      字符串
     * @param splitter 分隔符
     * @return 分隔符后的内容，如果没有找到分隔符或字符串为空则返回null
     */
    private @Nullable Splitted splitLast(@NotNull String str, @NotNull String splitter) {
        if (str.isEmpty()) {
            return null;
        }
        int index = str.lastIndexOf(splitter);
        if (index == -1) {
            return null;
        }
        return new Splitted(
                str.substring(0, index).trim(),
                str.substring(index + splitter.length()).trim()
        );
    }

    /**
     * 检查字符串是否为数字
     *
     * @param str 字符串
     * @return 是否为数字
     */
    private boolean isNumber(@NotNull String str) {
        if (str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private EasyCondMetaData getCondMetaData(
            EasySearch easySearch,
            String className,
            String paramName
    ) {
        EasyCondMetaData metaData = null;
        if (className == null || className.isEmpty()) { //类名为空，使用主搜索类
            metaData = metaDataManager.getSearchMetaData(easySearch.getSearchClass()).getCond(
                    paramName);
        }
        else { //类名不为空，使用命名搜索类
            Class<?> searchClass = easySearch.getNamedSearchClass(className);
            if (searchClass == null) {
                return null;
            }
            EasySearchMetaData searchMetaData = metaDataManager.getSearchMetaData(searchClass);
            if (searchMetaData == null) {
                return null;
            }
            metaData = searchMetaData.getCond(paramName);
        }
        return metaData;
    }

    public static class Splitted {
        private final String left;
        private final String right;

        public Splitted(String left, String right) {
            this.left = left;
            this.right = right;
        }

        public String getLeft() {
            return left;
        }

        public String getRight() {
            return right;
        }
    }
}
