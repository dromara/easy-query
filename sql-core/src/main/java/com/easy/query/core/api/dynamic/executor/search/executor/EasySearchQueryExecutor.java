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
public interface EasySearchQueryExecutor {
    void whereObject(Object object,EntityQueryExpressionBuilder entityQueryExpressionBuilder);
    void orderByObject(ObjectSort objectSort, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
}
