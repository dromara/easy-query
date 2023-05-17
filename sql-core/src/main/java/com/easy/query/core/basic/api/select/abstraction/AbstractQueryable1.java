package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.provider.EasyQuerySQLBuilderProvider;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.basic.api.select.provider.Select1SQLProvider;

/**
 * @FileName: AbstractQueryable1.java
 * @Description: 文件说明
 * @Date: 2023/3/4 14:12
 * @author xuejiaming
 */
public abstract class AbstractQueryable1<T> extends AbstractQueryable<T> {
    protected final Select1SQLProvider<T> sqlPredicateProvider;
    public AbstractQueryable1(Class<T> tClass, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(tClass, sqlEntityExpression);
//        EntityMetadata entityMetadata = this.sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t1Class);

        sqlPredicateProvider = new Select1SQLProvider<>(this.entityQueryExpressionBuilder);
    }

    @Override
    public EasyQuerySQLBuilderProvider<T> getSQLBuilderProvider1() {
        return sqlPredicateProvider;
    }
}
