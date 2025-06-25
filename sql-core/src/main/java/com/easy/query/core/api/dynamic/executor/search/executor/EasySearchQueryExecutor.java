package com.easy.query.core.api.dynamic.executor.search.executor;

import com.easy.query.core.api.dynamic.executor.query.WhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.search.EasySearch;
import com.easy.query.core.api.dynamic.executor.search.EasySearchConfiguration;
import com.easy.query.core.api.dynamic.executor.search.EasySortType;
import com.easy.query.core.api.dynamic.executor.search.exception.EasySearchStatusException;
import com.easy.query.core.api.dynamic.executor.search.match.EasyTableMatch;
import com.easy.query.core.api.dynamic.executor.search.meta.EasyCondMetaData;
import com.easy.query.core.api.dynamic.executor.search.meta.EasySearchMetaDataManager;
import com.easy.query.core.api.dynamic.executor.search.op.FilterContext;
import com.easy.query.core.api.dynamic.executor.search.op.Op;
import com.easy.query.core.api.dynamic.executor.search.option.EasySearchOption;
import com.easy.query.core.api.dynamic.executor.search.param.ParamMap;
import com.easy.query.core.api.dynamic.executor.search.param.EasySearchParamMapProvider;
import com.easy.query.core.api.dynamic.executor.sort.ObjectSortQueryExecutor;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.impl.OrderSelectorImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasySQLUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * OrderSort执行器
 *
 * @author bkbits
 */
public class EasySearchQueryExecutor
        implements WhereObjectQueryExecutor, ObjectSortQueryExecutor {
    /**
     * 配置对象
     */
    private final EasySearchConfiguration configuration;
    /**
     * 解析器
     */
    private final EasySearchParamParser parser;
    /**
     * 参数提供器
     */
    private final EasySearchParamMapProvider paramMapProvider;

    /**
     * 运算符参数后缀
     */
    private final String opSuffix;

    public EasySearchQueryExecutor(EasySearchMetaDataManager metaDataManager) {
        this.configuration = metaDataManager.getConfiguration();
        this.paramMapProvider = configuration.getParamMapProvider();
        this.parser = new EasySearchParamParser(
                metaDataManager,
                configuration
        );
        this.opSuffix = this.configuration.getOption().getParamSplitter() + "op";
    }

    @Override
    public void whereObject(
            Object object,
            EntityQueryExpressionBuilder entityQueryExpressionBuilder
    ) {
        if (!(object instanceof EasySearch)) {
            return;
        }

        EasySearch easySearch = (EasySearch) object;
        EasySearchOption option = configuration.getOption();
        final ParamMap paramMap = getParamMap(easySearch);
        if (paramMap == null) {
            return;
        }

        EasySearchParams condParams = new EasySearchParams();

        //解析并排序参数
        for (String paramNameStr : paramMap.getParamNames()) {
            if (option.getExcludes().contains(paramNameStr) ||
                    paramNameStr.equals(option.getSortParam()) ||
                    paramNameStr.endsWith(opSuffix)) {
                //跳过需要排除的参数，包括excludes，sort, [name]-op参数
                continue;
            }

            EasySearchParamName paramName = parser.parseParamName(easySearch, paramNameStr);
            if (paramName == null) {
                if (configuration.isStrict()) { //严格模式抛出异常
                    throw new EasySearchStatusException("非法参数：" + paramNameStr);
                }
                continue;
            }
            List<Object> values = paramMap.getParamValues(paramNameStr);
            condParams.addCondParam(
                    paramName,
                    values,
                    paramName.getIndex(),
                    () -> {
                        String opName = parser.getOpParamName(paramNameStr);
                        String opParamStr = paramMap.getParamString(opName, null);
                        Op op;
                        if (opParamStr == null) { //没有设置运算符，使用默认运算符
                            op = configuration.getOp(paramName.getMetaData().getCond());
                        }
                        else { //设置了运算符符号，获取对应实例
                            op = configuration.getOp(opParamStr.trim());
                        }

                        if (op == null) {
                            //找不到对应的运算符
                            throw new EasySearchStatusException(paramNameStr + "：非法的运算符");
                        }
                        return new EasySearchParam(paramName, op);
                    }
            );
        }

        Filter filter = new EasySearchFilter(
                entityQueryExpressionBuilder.getRuntimeContext(),
                entityQueryExpressionBuilder.getExpressionContext(),
                entityQueryExpressionBuilder.getWhere(),
                false,
                AnyValueFilter.DEFAULT
        );

        condParams.getUngroupParams().forEach((k, condParam) -> {
            //此处需要先进行一次参数值排序
            condParam.sort();
            EasyCondMetaData metaData = condParam.getParamName().getMetaData();
            EasyTableMatch tableMatch = metaData.getTableMatch();
            if (tableMatch == null) {
                tableMatch = condParam.getParamName().getTableMatch();
            }
            TableAvailable table = tableMatch.getTable(entityQueryExpressionBuilder);
            if (table == null) { //无法找到匹配的表
                if (configuration.isStrict()) { //严格模式抛出异常
                    throw new EasySearchStatusException("无法找到匹配的表：" + tableMatch);
                }
                return;
            }

            // 检查运算符约束
            metaData.checkCondEnabled(condParam.getOp().getClass());

            //添加过滤条件
            condParam.getOp().filter(
                    new FilterContext(
                            filter,
                            table,
                            condParam.getParams()
                                     .stream()
                                     .map(EasySearchValue::getValue)
                                     .collect(Collectors.toList()),
                            metaData.getColumn()
                    )
            );
        });
    }

    @Override
    public void orderByObject(
            ObjectSort objectSort,
            EntityQueryExpressionBuilder entityQueryExpressionBuilder
    ) {
        if (!(objectSort instanceof EasySearch)) {
            return;
        }

        EasySearch easySearch = (EasySearch) objectSort;
        EasySearchOption option = configuration.getOption();
        final ParamMap paramMap = getParamMap(easySearch);

        List<String> sorts = null;
        if (paramMap == null || (sorts = paramMap.getParamStrings(option.getSortParam())) == null) {
            sorts = easySearch.getDefaultSort();
        }
        if (sorts == null || sorts.isEmpty()) { //排序参数为空
            return;
        }

        orderByParams(entityQueryExpressionBuilder, easySearch, sorts);
    }

    private void orderByParams(
            EntityQueryExpressionBuilder entityQueryExpressionBuilder,
            EasySearch easySearch,
            List<String> sortStrs
    ) {
        //排序，检查过滤不合法的排序参数，排序
        sortStrs.stream()
                .map(sort -> {
                    EasySearchSortParam sortParam = parser.parseSort(
                            easySearch,
                            sort
                    );
                    if (sortParam == null && configuration.isStrict()) {
                        throw new EasySearchStatusException("排序参数不合法：" + sort);
                    }
                    return sortParam;
                })
                .filter(Objects::nonNull)
                .forEach(sortParam -> this.orderBySortItem(
                        sortParam,
                        entityQueryExpressionBuilder
                ));
    }

    /**
     * 排序
     *
     * @param sortParam                    排序参数
     * @param entityQueryExpressionBuilder 语法构造器
     */
    private void orderBySortItem(
            EasySearchSortParam sortParam,
            EntityQueryExpressionBuilder entityQueryExpressionBuilder
    ) {
        OrderSelectorImpl orderSelector = new OrderSelectorImpl(
                entityQueryExpressionBuilder,
                entityQueryExpressionBuilder.getRuntimeContext(),
                entityQueryExpressionBuilder.getExpressionContext(),
                entityQueryExpressionBuilder.getOrder()
        );

        EasyTableMatch tableMatch = sortParam.getMetaData().getTableMatch();
        if (tableMatch == null) {
            tableMatch = sortParam.getTableMatch();
        }

        TableAvailable table = tableMatch.getTable(entityQueryExpressionBuilder);
        EasySortType sortType = sortParam.getSortType();
        if (sortType == null) {
            sortType = sortParam.getMetaData().getSort();
        }

        sortParam.getMetaData().checkSortEnabled(sortType != null ? sortType : EasySortType.Asc);

        // 使用sortOrder进行优先级排序
        EasySQLUtil.dynamicOrderBy(
                orderSelector,
                table,
                sortParam.getMetaData().getPropertyName(),
                sortType != EasySortType.Desc,
                null,
                true
        );
    }


    private ParamMap getParamMap(EasySearch easySearch) {
        EasySearchOption option = configuration.getOption();
        ParamMap paramMapNullable = easySearch.getParamMap();
        if (paramMapNullable == null) { //参数为空
            if (paramMapProvider == null) { //参数提供器为空，直接返回null
                return null;
            }
            //使用参数提供器构造参数
            paramMapNullable = paramMapProvider.getParamMap();
        }
        return paramMapNullable;
    }
}
