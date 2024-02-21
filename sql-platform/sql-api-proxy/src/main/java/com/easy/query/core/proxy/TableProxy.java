package com.easy.query.core.proxy;


import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
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

   default TProxy create(TableAvailable table, EntityExpressionBuilder entityExpressionBuilder, QueryRuntimeContext runtimeContext){
       if(getEntitySQLContext() instanceof ProxyEntitySQLContext){
           return create(table,getEntitySQLContext());
       }
       return create(table,new ProxyEntitySQLContext(entityExpressionBuilder,runtimeContext));
   }
    TProxy create(TableAvailable table, EntitySQLContext entitySQLContext);
}
