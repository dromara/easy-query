package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.columns.SubQuerySQLQueryableFactory;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;

/**
 * create time 2025/3/12 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSubQuerySQLQueryableFactory implements SubQuerySQLQueryableFactory {
    public static final SubQuerySQLQueryableFactory INSTANCE = new DefaultSubQuerySQLQueryableFactory();

    public <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> SQLQueryable<T1Proxy, T1> create(SubQueryContext<T1Proxy, T1> subQueryContext) {

        EntityExpressionBuilder entityExpressionBuilder = subQueryContext.getEntityExpressionBuilder();
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        TableAvailable leftTable = subQueryContext.getLeftTable();
        String property = subQueryContext.getProperty();
        T1Proxy propertyProxy = subQueryContext.getPropertyProxy();
        String fullName = subQueryContext.getFullName();
        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
        DefaultRelationTableKey defaultRelationTableKey = new DefaultRelationTableKey(leftTable.getEntityClass(), navigateMetadata.getNavigatePropertyType(), fullName);
        if (subQueryContext.getConfigureExpression() != null || subQueryContext.getOrderByExpression() != null || !subQueryContext.hasElements()) {

            if (entityExpressionBuilder.hasManyJoinConfiguration(defaultRelationTableKey)) {
                ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);
                AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder = EasyRelationalUtil.getManyJoinRelationTable(entityExpressionBuilder, leftTable, navigateMetadata, fullName, manyConfiguration.getConfigureExpression());

                EntityTableExpressionBuilder manyJoinTableExpressionBuilder = anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder().getTable(0);
                T1Proxy manyJoinPropertyProxy = propertyProxy.create(manyJoinTableExpressionBuilder.getEntityTable(), anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder(), runtimeContext);
                manyJoinPropertyProxy.setNavValue(fullName);
                return new EasyManyJoinSQLManyQueryable<>(subQueryContext, anonymousManyJoinEntityTableExpressionBuilder, manyJoinPropertyProxy);
            }
        }
        ClientQueryable<T1> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(propertyProxy.getEntityClass(), runtimeContext);
        ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);
        if (manyConfiguration != null) {
            clientQueryable = EasyObjectUtil.typeCastNullable(manyConfiguration.getConfigureExpression().apply(clientQueryable));
        }
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            ClientQueryable<?> mappingQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
            clientQueryable.where(x -> {
                x.and(() -> {
                    ClientQueryable<?> subMappingQueryable = mappingQueryable.where(m -> {
                        m.multiEq(true, x, navigateMetadata.getTargetMappingProperties(), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext));
                        m.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getSelfMappingProperties(), navigateMetadata.getSelfPropertiesOrPrimary());
                        navigateMetadata.predicateMappingClassFilterApply(m);
                    }).limit(1);
                    x.exists(subMappingQueryable);
                    navigateMetadata.predicateFilterApply(x);
                });
            });
        } else {
            clientQueryable.where(t -> {
                t.and(() -> {
                    t.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                    navigateMetadata.predicateFilterApply(t);
                });
            });
        }
        EasyEntityQueryable<T1Proxy, T1> queryable = new EasyEntityQueryable<>(propertyProxy, clientQueryable);
        queryable.get1Proxy().setNavValue(fullName);
        EasySQLManyQueryable<T1Proxy, T1> query = new EasySQLManyQueryable<>(subQueryContext, queryable);
        return query;
    }

    @Override
    public <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy create(SubQueryContext<T1Proxy, T1> subQueryContext, int index) {
        return null;
    }
}
