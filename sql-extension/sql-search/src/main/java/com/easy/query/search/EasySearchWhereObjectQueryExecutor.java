package com.easy.query.search;

import com.easy.query.core.api.dynamic.executor.query.DefaultWhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.query.WhereConditionProvider;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.search.executor.EasySearchQueryExecutor;

/**
 * create time 2025/6/25 21:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySearchWhereObjectQueryExecutor extends DefaultWhereObjectQueryExecutor {
    private final EasySearchQueryExecutor easySearchQueryExecutor;

    public EasySearchWhereObjectQueryExecutor(EasySearchQueryExecutor easySearchQueryExecutor, WhereConditionProvider whereConditionProvider){
        super(whereConditionProvider);
        this.easySearchQueryExecutor = easySearchQueryExecutor;
    }
    @Override
    public void whereObject(Object object, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        if (object instanceof EasySearch) {
            easySearchQueryExecutor.whereObject(object, entityQueryExpressionBuilder);
            return;
        }
        super.whereObject(object, entityQueryExpressionBuilder);
    }
}
