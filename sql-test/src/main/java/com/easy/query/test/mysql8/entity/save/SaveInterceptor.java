package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

/**
 * create time 2025/9/8 09:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class SaveInterceptor implements EntityInterceptor{
    @Override
    public void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpressionBuilder, Object entity) {

        EntityMetadata entityMetadata = entityInsertExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(entityClass);

        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        if(EasyCollectionUtil.isSingle(keyProperties)){
            String singleKey = EasyCollectionUtil.first(keyProperties);
            ColumnMetadata columnOrNull = entityMetadata.getColumnOrNull(singleKey);
            if(columnOrNull!=null){
                Object idValue=columnOrNull.getGetterCaller().apply(entity);
                if(idValue==null){
                    columnOrNull.getSetterCaller().call(entity, UUID.randomUUID().toString());
                }
            }
        }
    }

    @Override
    public void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, Object entity) {

    }

    @Override
    public String name() {
        return "SaveInterceptor";
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return M8SaveRoot.class.equals(entityClass)
                || M8SaveRoot2Many.class.equals(entityClass)
                || M8SaveRootMany.class.equals(entityClass)
                || M8SaveRootMiddleMany.class.equals(entityClass)
                || M8SaveRootOne.class.equals(entityClass)
                || M8SaveRootManyOne.class.equals(entityClass);
    }
}
