package org.easy.query.core.basic.api.select;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.impl.Select1SqlProvider;
import org.easy.query.core.query.EasyEntityTableExpression;
import org.easy.query.core.query.SqlExpressionContext;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.util.EasyUtil;

/**
 * @FileName: AbstractQueryable1.java
 * @Description: 文件说明
 * @Date: 2023/3/4 14:12
 * @Created by xuejiaming
 */
public abstract class AbstractQueryable1<T> extends AbstractQueryable<T> {
    protected final Select1SqlProvider<T> sqlPredicateProvider;
    public AbstractQueryable1(Class<T> tClass, SqlEntityQueryExpression sqlEntityExpression) {
        super(tClass, sqlEntityExpression);
//        EntityMetadata entityMetadata = this.sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t1Class);

        sqlPredicateProvider = new Select1SqlProvider<>(this.sqlEntityExpression);
    }

    @Override
    public EasyQuerySqlBuilderProvider<T> getSqlBuilderProvider1() {
        return sqlPredicateProvider;
    }
}
