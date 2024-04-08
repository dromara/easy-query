package com.easy.query.core.expression.segment.factory;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.OrderFuncColumnSegment;
import com.easy.query.core.expression.segment.SQLLazySegement;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.SelectCountDistinctSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.impl.AnonymousColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.ColumnWithSelfSegmentImpl;
import com.easy.query.core.expression.segment.impl.FuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.GroupBySQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.impl.GroupColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.InsertColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.InsertMapColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.OrderBySQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.impl.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.OrderFuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.SQLColumnAsSegmentImpl;
import com.easy.query.core.expression.segment.impl.SQLNativeLazySegmentImpl;
import com.easy.query.core.expression.segment.impl.SQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.impl.SelectConstSegmentImpl;
import com.easy.query.core.expression.segment.impl.SelectCountDistinctSegmentImpl;
import com.easy.query.core.expression.segment.impl.SubQueryColumnSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/5/30 12:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLSegmentFactory implements SQLSegmentFactory {
    @Override
    public SelectConstSegment createSelectConstSegment(String projects) {
        return new SelectConstSegmentImpl(projects);
    }

    @Override
    public SelectCountDistinctSegment createSelectCountDistinctSegment(Collection<SQLSegment> sqlSegments) {
        return new SelectCountDistinctSegmentImpl(sqlSegments);
    }

    @Override
    public ColumnSegment createColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, String alias) {
        return new ColumnSegmentImpl(table, columnMetadata, expressionContext, alias);
    }

    @Override
    public ColumnSegment createAnonymousColumnSegment(TableAvailable table, ExpressionContext expressionContext, String alias) {
        return new AnonymousColumnSegmentImpl(table, expressionContext, alias);
    }

//    @Override
//    public ColumnAsConstSegment createColumnAsConstSegment(TableAvailable table, QueryRuntimeContext runtimeContext, String columnConst, String alias) {
//        return new ColumnAsConstSegmentImpl(table, runtimeContext, columnConst, alias);
//    }

    @Override
    public SQLNativeSegment createSQLNativeSegment(ExpressionContext expressionContext, String columnConst, SQLNativeExpression sqlNativeExpression) {
        return new SQLNativeSegmentImpl(expressionContext,columnConst,sqlNativeExpression);
    }

    @Override
    public SQLNativeSegment createSQLNativeLazySegment(ExpressionContext expressionContext, SQLLazySegement sqlLazySegement, Function<String, String> sqlSegementFunction, SQLNativeExpression sqlNativeExpression) {
        return new SQLNativeLazySegmentImpl(expressionContext,sqlLazySegement,sqlSegementFunction,sqlNativeExpression);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createInsertColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext) {
        return new InsertColumnSegmentImpl(table, columnMetadata, expressionContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createInsertMapColumnSegment(String columnName, QueryRuntimeContext runtimeContext) {
        return new InsertMapColumnSegmentImpl(columnName,runtimeContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createColumnWithSelfSegment(boolean increment, TableAvailable table, String propertyName, Object val, ExpressionContext expressionContext) {
        return new ColumnWithSelfSegmentImpl(increment, table, propertyName, val, expressionContext);
    }

    @Override
    public FuncColumnSegment createFuncColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, ColumnFunction columnFunction, String alias) {
        return new FuncColumnSegmentImpl(table, columnMetadata, expressionContext, columnFunction, alias);
    }

    @Override
    public GroupByColumnSegment createGroupByColumnSegment(TableAvailable table,ColumnMetadata columnMetadata, ExpressionContext expressionContext) {
        return new GroupColumnSegmentImpl(table, columnMetadata, expressionContext);
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, boolean asc) {
        return new OrderColumnSegmentImpl(table, columnMetadata, expressionContext, asc);
    }

    @Override
    public OrderFuncColumnSegment createOrderFuncColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, ColumnFunction columnFunction, boolean asc) {
        return new OrderFuncColumnSegmentImpl(table, columnMetadata, expressionContext, columnFunction, asc);
    }

    @Override
    public OrderBySegment createOrderBySQLNativeSegment(ExpressionContext expressionContext, String columnConst, SQLNativeExpressionContext sqlConstExpressionContext, boolean asc) {
        return new OrderBySQLNativeSegmentImpl(expressionContext, columnConst,sqlConstExpressionContext, asc);
    }

    @Override
    public GroupByColumnSegment createGroupBySQLNativeSegment(ExpressionContext expressionContext, String columnConst, SQLNativeExpressionContext sqlConstExpressionContext) {
        return new GroupBySQLNativeSegmentImpl(expressionContext, columnConst,sqlConstExpressionContext);
    }

    @Override
    public SubQueryColumnSegment createSubQueryColumnSegment(TableAvailable table, Query<?> subQuery, String alias, QueryRuntimeContext runtimeContext) {
        return new SubQueryColumnSegmentImpl(table, subQuery, alias, runtimeContext);
    }

    @Override
    public CloneableSQLSegment createSQLColumnAsSegment(CloneableSQLSegment sqlColumnSegment, String alias, QueryRuntimeContext runtimeContext) {
        return new SQLColumnAsSegmentImpl(sqlColumnSegment,alias,runtimeContext);
    }
}
