package com.easy.query.core.proxy;


import com.easy.query.core.context.EmptyQueryRuntimeContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.EmptyTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EmptyEntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyEntitySQLContext;

import java.io.Serializable;

/**
 * create time 2023/6/21 16:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableProxy<TProxy extends TableProxy<TProxy, TEntity>, TEntity> extends BeanProxy, EntitySQLTableOwner<TEntity>, EntitySQLContextAvailable, Serializable {

//    default boolean _isDefault() {
//        return getTable() == null;
//    }

    Class<TEntity> getEntityClass();

    /**
     * 框架内部api用户无需调用
     * @param table
     * @param entityExpressionBuilder
     * @return
     */
    default TProxy create(TableAvailable table, EntityExpressionBuilder entityExpressionBuilder) {
        return create(table, entityExpressionBuilder,entityExpressionBuilder.getRuntimeContext());
    }

    /**
     * 框架内部api用户无需调用
     * @param table
     * @param entityExpressionBuilder
     * @param runtimeContext
     * @return
     */
    default TProxy create(TableAvailable table, EntityExpressionBuilder entityExpressionBuilder, QueryRuntimeContext runtimeContext) {
        EntitySQLContext entitySQLContext = getEntitySQLContext();
        if (entitySQLContext instanceof ProxyEntitySQLContext && entitySQLContext.getEntityExpressionBuilder() == entityExpressionBuilder) {
            return create(table, entitySQLContext);
        }
        return create(table, new ProxyEntitySQLContext(entityExpressionBuilder, runtimeContext));
    }

    /**
     * 框架内部api用户无需调用
     * @return
     */
    default TProxy createEmpty() {
        return create(EmptyTableAvailable.DEFAULT, EmptyEntityExpressionBuilder.DEFAULT, EmptyQueryRuntimeContext.DEFAULT);
    }

    /**
     * 框架内部api用户无需调用
     * @param table
     * @param entitySQLContext
     * @return
     */
    TProxy create(TableAvailable table, EntitySQLContext entitySQLContext);

    /**
     * 框架内部api用户无需调用
     * @return
     */
    default TProxy createNew() {
        throw new UnsupportedOperationException("not support createNew");
    }
}
