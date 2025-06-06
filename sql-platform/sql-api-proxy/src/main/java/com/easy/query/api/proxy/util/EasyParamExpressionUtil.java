package com.easy.query.api.proxy.util;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.FormatValueParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.segment.scec.expression.SQLSegmentParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.SubQueryParamExpressionImpl;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
}
