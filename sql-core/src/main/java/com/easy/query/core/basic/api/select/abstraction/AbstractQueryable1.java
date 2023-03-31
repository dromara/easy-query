package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.basic.api.select.provider.Select1SqlProvider;

/**
 * @FileName: AbstractQueryable1.java
 * @Description: 文件说明
 * @Date: 2023/3/4 14:12
 * @author xuejiaming
 */
public abstract class AbstractQueryable1<T> extends AbstractQueryable<T> {
    protected final Select1SqlProvider<T> sqlPredicateProvider;
    public AbstractQueryable1(Class<T> tClass, EntityQueryExpression sqlEntityExpression) {
        super(tClass, sqlEntityExpression);
//        EntityMetadata entityMetadata = this.sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t1Class);

        sqlPredicateProvider = new Select1SqlProvider<>(this.sqlEntityExpression);
    }

    @Override
    public EasyQuerySqlBuilderProvider<T> getSqlBuilderProvider1() {
        return sqlPredicateProvider;
    }
}
