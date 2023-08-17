package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable4.SQLAggregatable4;
import com.easy.query.api4j.select.extension.queryable4.SQLFilterable4;
import com.easy.query.api4j.select.extension.queryable4.SQLGroupable4;
import com.easy.query.api4j.select.extension.queryable4.SQLHavingable4;
import com.easy.query.api4j.select.extension.queryable4.SQLOrderable4;
import com.easy.query.api4j.select.extension.queryable4.SQLSelectable4;
import com.easy.query.api4j.select.extension.queryable4.override.OverrideQueryable4;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable4<T1, T2, T3, T4> extends OverrideQueryable4<T1, T2, T3, T4>,
        SQLAggregatable4<T1, T2, T3, T4>,
        SQLFilterable4<T1, T2, T3, T4>,
        SQLGroupable4<T1, T2, T3, T4> ,
        SQLHavingable4<T1, T2, T3, T4>,
        SQLOrderable4<T1, T2, T3, T4>,
        SQLSelectable4<T1, T2, T3, T4> {
}
