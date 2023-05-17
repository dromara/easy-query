package com.easy.query.core.expression.sql.expression.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.expression.EasyAnonymousQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EasyDeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSQLExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSQLExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousUnionQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.InsertSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.QuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpression;

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
    public EasyQuerySQLExpression createEasyQuerySQLExpression(EasyQueryRuntimeContext runtimeContext) {
        return new QuerySQLExpression(runtimeContext);
    }

    @Override
    public EasyInsertSQLExpression createEasyInsertSQLExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSQLExpression easyTableSQLExpression) {
        return new InsertSQLExpression(runtimeContext, easyTableSQLExpression);
    }

    @Override
    public EasyUpdateSQLExpression createEasyUpdateSQLExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSQLExpression easyTableSQLExpression) {
        return new UpdateSQLExpression(runtimeContext, easyTableSQLExpression);
    }

    @Override
    public EasyDeleteSQLExpression createEasyDeleteSQLExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSQLExpression easyTableSQLExpression) {
        return new DeleteSQLExpression(runtimeContext, easyTableSQLExpression);
    }

    @Override
    public EasyAnonymousQuerySQLExpression createEasyAnonymousQuerySQLExpression(EasyQueryRuntimeContext runtimeContext, String sql) {
        return new AnonymousQuerySQLExpression(runtimeContext,sql);
    }

    @Override
    public EasyAnonymousQuerySQLExpression createEasyAnonymousUnionQuerySQLExpression(EasyQueryRuntimeContext runtimeContext, List<EasyQuerySQLExpression> easyQuerySQLExpressions, SQLUnionEnum sqlUnion) {
        return new AnonymousUnionQuerySqlExpression(runtimeContext,new ArrayList<>(easyQuerySQLExpressions),sqlUnion);
    }
}
