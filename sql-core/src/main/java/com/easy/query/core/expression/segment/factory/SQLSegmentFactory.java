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
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/5/28 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSegmentFactory {
    SelectConstSegment createSelectConstSegment(String projects);
    SelectCountDistinctSegment createSelectCountDistinctSegment(Collection<SQLSegment> sqlSegments);
//    ColumnSegment createColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String alias);
    default ColumnSegment createColumnSegment(TableAvailable table, String propertyName, ExpressionContext expressionContext, String alias){
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return createColumnSegment(table,columnMetadata,expressionContext,alias);
    }
    ColumnSegment createColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, String alias);
    ColumnSegment createAnonymousColumnSegment(TableAvailable table, ExpressionContext expressionContext, String alias);
//    ColumnAsConstSegment createColumnAsConstSegment(TableAvailable table, QueryRuntimeContext runtimeContext,String columnConst, String alias);
    SQLNativeSegment createSQLNativeSegment(ExpressionContext expressionContext, String columnConst, SQLNativeExpression sqlNativeExpression);
    SQLNativeSegment createSQLNativeLazySegment(ExpressionContext expressionContext, SQLLazySegement sqlLazySegement, Function<String,String> sqlSegementFunction, SQLNativeExpression sqlNativeExpression);
    InsertUpdateSetColumnSQLSegment createInsertColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext);
    InsertUpdateSetColumnSQLSegment createInsertMapColumnSegment(String columnName, QueryRuntimeContext runtimeContext);
    InsertUpdateSetColumnSQLSegment createColumnWithSelfSegment(boolean increment, TableAvailable table, String propertyName, Object val, ExpressionContext expressionContext);
    default FuncColumnSegment createFuncColumnSegment(TableAvailable table, String propertyName, ExpressionContext expressionContext, ColumnFunction columnFunction, String alias){
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return createFuncColumnSegment(table,columnMetadata,expressionContext,columnFunction,alias);
    }
  FuncColumnSegment createFuncColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, ColumnFunction columnFunction, String alias);
    default GroupByColumnSegment createGroupByColumnSegment(TableAvailable table, String propertyName, ExpressionContext expressionContext){
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return createGroupByColumnSegment(table,columnMetadata,expressionContext);
    }
    GroupByColumnSegment createGroupByColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext);
   default OrderBySegment createOrderByColumnSegment(TableAvailable table, String propertyName, ExpressionContext expressionContext, boolean asc){
       ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
       return createOrderByColumnSegment(table,columnMetadata,expressionContext,asc);
   }
    OrderBySegment createOrderByColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, boolean asc);

   default OrderFuncColumnSegment createOrderFuncColumnSegment(TableAvailable table, String propertyName, ExpressionContext expressionContext, ColumnFunction columnFunction, boolean asc){
       ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
       return createOrderFuncColumnSegment(table,columnMetadata,expressionContext,columnFunction,asc);
   }
    OrderFuncColumnSegment createOrderFuncColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, ColumnFunction columnFunction, boolean asc);
    OrderBySegment createOrderBySQLNativeSegment(ExpressionContext expressionContext, String columnConst, SQLNativeExpressionContext sqlConstExpressionContext, boolean asc);
    GroupByColumnSegment createGroupBySQLNativeSegment(ExpressionContext expressionContext, String columnConst, SQLNativeExpressionContext sqlConstExpressionContext);

    SubQueryColumnSegment createSubQueryColumnSegment(TableAvailable table, Query<?> subQuery, String alias, QueryRuntimeContext runtimeContext);
    CloneableSQLSegment createSQLColumnAsSegment(CloneableSQLSegment sqlColumnSegment, String alias, QueryRuntimeContext runtimeContext);
}
