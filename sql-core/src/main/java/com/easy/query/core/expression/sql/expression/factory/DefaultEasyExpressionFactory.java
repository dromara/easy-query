package com.easy.query.core.expression.sql.expression.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.expression.AnonymousQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.DeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.InsertSQLExpression;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.expression.sql.expression.UpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousQuerySQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.AnonymousUnionQuerySQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.InsertSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.QuerySQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/26 15:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyExpressionFactory implements EasyExpressionFactory{
    @Override
    public QuerySQLExpression createEasyQuerySQLExpression(EasyQueryRuntimeContext runtimeContext) {
        return new QuerySQLExpressionImpl(runtimeContext);
    }

    @Override
    public InsertSQLExpression createEasyInsertSQLExpression(EasyQueryRuntimeContext runtimeContext, TableSQLExpression easyTableSQLExpression) {
        return new InsertSQLExpressionImpl(runtimeContext, easyTableSQLExpression);
    }

    @Override
    public UpdateSQLExpression createEasyUpdateSQLExpression(EasyQueryRuntimeContext runtimeContext, TableSQLExpression easyTableSQLExpression) {
        return new UpdateSQLExpressionImpl(runtimeContext, easyTableSQLExpression);
    }

    @Override
    public DeleteSQLExpression createEasyDeleteSQLExpression(EasyQueryRuntimeContext runtimeContext, TableSQLExpression easyTableSQLExpression) {
        return new DeleteSQLExpressionImpl(runtimeContext, easyTableSQLExpression);
    }

    @Override
    public AnonymousQuerySQLExpression createEasyAnonymousQuerySQLExpression(EasyQueryRuntimeContext runtimeContext, String sql) {
        return new AnonymousQuerySQLExpressionImpl(runtimeContext,sql);
    }

    @Override
    public AnonymousQuerySQLExpression createEasyAnonymousUnionQuerySQLExpression(EasyQueryRuntimeContext runtimeContext, List<QuerySQLExpression> easyQuerySQLExpressions, SQLUnionEnum sqlUnion) {
        return new AnonymousUnionQuerySQLExpressionImpl(runtimeContext,new ArrayList<>(easyQuerySQLExpressions),sqlUnion);
    }
}
