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
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/5/28 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSegmentFactory {
    SelectConstSegment createSelectConstSegment(String projects);
//    ColumnSegment createColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String alias);
    default ColumnSegment createColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String alias){
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return createColumnSegment(table,columnMetadata,runtimeContext,alias);
    }
    ColumnSegment createColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, String alias);
    ColumnSegment createAnonymousColumnSegment(TableAvailable table, QueryRuntimeContext runtimeContext, String alias);
//    ColumnAsConstSegment createColumnAsConstSegment(TableAvailable table, QueryRuntimeContext runtimeContext,String columnConst, String alias);
    SQLNativeSegment createSQLNativeSegment(QueryRuntimeContext runtimeContext, String columnConst, SQLNativeExpression sqlNativeExpression);
    InsertUpdateSetColumnSQLSegment createInsertColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext);
    InsertUpdateSetColumnSQLSegment createColumnWithSelfSegment(boolean increment, TableAvailable table, String propertyName, Object val, QueryRuntimeContext runtimeContext);
    default FuncColumnSegment createFuncColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, String alias){
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return createFuncColumnSegment(table,columnMetadata,runtimeContext,columnFunction,alias);
    }
  FuncColumnSegment createFuncColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, String alias);
    default GroupByColumnSegment createGroupByColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return createGroupByColumnSegment(table,columnMetadata,runtimeContext);
    }
    GroupByColumnSegment createGroupByColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext);
   default OrderBySegment createOrderByColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, boolean asc){
       ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
       return createOrderByColumnSegment(table,columnMetadata,runtimeContext,asc);
   }
    OrderBySegment createOrderByColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, boolean asc);

   default OrderFuncColumnSegment createOrderFuncColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, boolean asc){
       ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
       return createOrderFuncColumnSegment(table,columnMetadata,runtimeContext,columnFunction,asc);
   }
    OrderFuncColumnSegment createOrderFuncColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, boolean asc);
    OrderBySegment createOrderBySQLNativeSegment(QueryRuntimeContext runtimeContext, String columnConst, SQLNativeExpressionContext sqlConstExpressionContext, boolean asc);
    GroupByColumnSegment createGroupBySQLNativeSegment(QueryRuntimeContext runtimeContext, String columnConst, SQLNativeExpressionContext sqlConstExpressionContext);

    SubQueryColumnSegment createSubQueryColumnSegment(TableAvailable table, Query<?> subQuery, String alias, QueryRuntimeContext runtimeContext);
    CloneableSQLSegment createSQLColumnAsSegment(CloneableSQLSegment sqlColumnSegment, String alias, QueryRuntimeContext runtimeContext);
}
