package com.easy.query.api.proxy.util;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.FormatValueParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.segment.scec.expression.SQLSegmentParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.SubQueryParamExpressionImpl;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * create time 2024/6/16 20:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyParamExpressionUtil {

    public static ParamExpression getParamExpression(@NotNull EntitySQLContext entitySQLContext, @Nullable Object value) {
        if (value == null) {
            return new FormatValueParamExpressionImpl("NULL");
        }
        if (value instanceof SQLColumn) {
            SQLColumn<?, ?> sqlColumn = (SQLColumn<?, ?>) value;
            return new ColumnPropertyExpressionImpl(sqlColumn.getTable(), sqlColumn.getValue(), entitySQLContext.getEntityExpressionBuilder().getExpressionContext());
        } else if (value instanceof Query) {
            Query<?> query = (Query<?>) value;
            return new SubQueryParamExpressionImpl(query);
        } else if (value instanceof DSLSQLFunctionAvailable) {
            DSLSQLFunctionAvailable dslSQLFunction = (DSLSQLFunctionAvailable) value;
            SQLFunction sqlFunction = dslSQLFunction.func().apply(entitySQLContext.getRuntimeContext().fx());
            ExpressionContext expressionContext = entitySQLContext.getEntityExpressionBuilder().getExpressionContext();
            return new SQLSegmentParamExpressionImpl(sqlFunction, expressionContext, dslSQLFunction.getTable(), expressionContext.getRuntimeContext(), null);
        } else {
            return new ColumnConstParameterExpressionImpl(value);
        }
    }
    public static ColumnFuncSelector acceptParameters(ColumnFuncSelector columnFuncSelector, @Nullable Object value) {
        if (value == null) {
            columnFuncSelector.value(null);
        }
        if (value instanceof SQLColumn) {
            SQLColumn<?, ?> sqlColumn = (SQLColumn<?, ?>) value;
            columnFuncSelector.column(sqlColumn);
        } else if (value instanceof Query) {
            Query<?> query = (Query<?>) value;
            columnFuncSelector.subQuery(query);
        } else if (value instanceof SQLFunction) {
            SQLFunction dslSQLFunction = (SQLFunction) value;
            columnFuncSelector.sqlFunc(dslSQLFunction);
        }  else if (value instanceof PredicateSegment) {
            PredicateSegment  sqlSegment = (PredicateSegment) value;
            columnFuncSelector.expression(sqlSegment);
        }  else if (value instanceof Collection) {
            Collection<?>  collection = (Collection) value;
            columnFuncSelector.collection(collection);
        }   else if (value instanceof PropTypeColumn) {
            PropTypeColumn<?>  propTypeColumn = (PropTypeColumn) value;
            PropTypeColumn.columnFuncSelector(columnFuncSelector, propTypeColumn);
        } else {
            columnFuncSelector.value(value);
        }
        return columnFuncSelector;
    }
}
