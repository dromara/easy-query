package com.easy.query.core.func.column;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.impl.CaseWhenSQLSegment;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.impl.ColumSQLExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFuncExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFuncFormatExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFunctionExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnLazyFunctionExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnMultiValueExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnNameExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnSubQueryExpressionImpl;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFuncSelectorImpl implements ColumnFuncSelector {
    private final List<ColumnExpression> concatExpressions;

    public ColumnFuncSelectorImpl(List<ColumnExpression> concatExpressions){

        this.concatExpressions = concatExpressions;
    }

    @Override
    public ColumnFuncSelector expression(PredicateSegment sqlSegment) {
        concatExpressions.add(new ColumSQLExpressionImpl(new CaseWhenSQLSegment(toSQLContext -> sqlSegment.toSQL(toSQLContext), visitor -> EasySQLSegmentUtil.tableVisit(sqlSegment, visitor))));
        return this;
    }

    @Override
    public ColumnFuncSelector column(String property) {
        concatExpressions.add(new ColumnFuncExpressionImpl(null,property));
        return this;
    }

    @Override
    public ColumnFuncSelector columnName(TableAvailable table, String columnName) {
        concatExpressions.add(new ColumnNameExpressionImpl(table,columnName));
        return this;
    }

    @Override
    public ColumnFuncSelector column(SQLTableOwner tableOwner, String property) {
        if(tableOwner!=null){
            return column(tableOwner.getTable(),property);
        }
        return column(property);
    }

    @Override
    public ColumnFuncSelector column(TableAvailable table, String property) {
        concatExpressions.add(new ColumnFuncExpressionImpl(table,property));
        return this;
    }

    @Override
    public ColumnFuncSelector value(Object val) {
        concatExpressions.add(new ColumnFuncValueExpressionImpl(val));
        return this;
    }

    @Override
    public <T> ColumnFuncSelector collection(Collection<T> collections) {
        concatExpressions.add(new ColumnMultiValueExpressionImpl(collections));
        return this;
    }

    @Override
    public ColumnFuncSelector sqlFuncExpression(TableAvailable table, Function<SQLFunc, SQLFunction> sqlFunctionCreator) {
        concatExpressions.add(new ColumnLazyFunctionExpressionImpl(table,sqlFunctionCreator));
        return this;
    }
    //    @Override
//    public ColumnFuncSelector sqlParameter(SQLParameter sqlParameter) {
//        concatExpressions.add(new ColumnFuncSQLParameterExpressionImpl(sqlParameter));
//        return this;
//    }

    @Override
    public ColumnFuncSelector format(Object valFormat) {
        concatExpressions.add(new ColumnFuncFormatExpressionImpl(valFormat));
        return this;
    }
//
//    @Override
//    public ColumnFuncSelector predicate(SQLExpression1<Filter> filterExpression) {
//        return null;
//    }

    @Override
    public ColumnFuncSelector sql(SQLSegment sqlSegment) {
        concatExpressions.add(new ColumSQLExpressionImpl(sqlSegment));
        return this;
    }

    @Override
    public ColumnFuncSelector sqlFunc(TableAvailable table,SQLFunction sqlFunction) {
        concatExpressions.add(new ColumnFunctionExpressionImpl(table,sqlFunction));
        return this;
//        SQLNativeExpressionContextImpl sqlNativeExpressionContextLeft = new SQLNativeExpressionContextImpl(null,runtimeContext);
//        sqlFunctionLeft.consume(new SQLNativeChainExpressionContextImpl(tableLeft,sqlNativeExpressionContextLeft));
//        String sqlSegmentLeft = sqlFunctionLeft.sqlSegment(tableLeft);
//        SQLNativePredicateImpl sqlNativePredicateLeft = new SQLNativePredicateImpl(runtimeContext, sqlSegmentLeft, sqlNativeExpressionContextLeft);
    }

    @Override
    public ColumnFuncSelector subQuery(Query<?> subQuery) {
        concatExpressions.add(new ColumnSubQueryExpressionImpl(subQuery));
        return this;
    }
}
