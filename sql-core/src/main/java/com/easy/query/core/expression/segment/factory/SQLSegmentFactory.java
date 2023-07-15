package com.easy.query.core.expression.segment.factory;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnAsConstSegment;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.ColumnWithSelfSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.OrderFuncColumnSegment;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;

/**
 * create time 2023/5/28 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSegmentFactory {
    SelectConstSegment createSelectConstSegment(String projects);
    ColumnSegment createColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String alias);
    ColumnSegment createAnonymousColumnSegment(TableAvailable table, QueryRuntimeContext runtimeContext, String alias);
    ColumnAsConstSegment createColumnAsConstSegment(TableAvailable table, QueryRuntimeContext runtimeContext,String columnConst, String alias);
    ColumnInsertSegment createColumnInsertSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext);
    ColumnWithSelfSegment createColumnWithSelfSegment(boolean increment, TableAvailable table, String propertyName, Object val, SQLPredicateCompare compare, QueryRuntimeContext runtimeContext);
    FuncColumnSegment createFuncColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, String alias);
    GroupByColumnSegment createGroupByColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext);
    OrderBySegment createOrderByColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, boolean asc);

    OrderFuncColumnSegment createOrderFuncColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, boolean asc);
    OrderBySegment createOrderByConstSegment(TableAvailable table, QueryRuntimeContext runtimeContext, String columnConst, boolean asc);
    GroupByColumnSegment createGroupByConstSegment(TableAvailable table, QueryRuntimeContext runtimeContext, String columnConst);

    SubQueryColumnSegment createSubQueryColumnSegment(TableAvailable table, Query<?> subQuery, String alias, QueryRuntimeContext runtimeContext);
    SQLColumnSegment createSQLColumnAsSegment(SQLColumnSegment sqlColumnSegment,String alias,QueryRuntimeContext runtimeContext);
}
