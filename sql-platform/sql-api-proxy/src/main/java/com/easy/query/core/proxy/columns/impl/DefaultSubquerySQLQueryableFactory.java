package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.implicit.EntityRelationToImplicitPartitionByProvider;
import com.easy.query.core.expression.parser.core.available.EmptyTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.columns.SubquerySQLQueryableFactory;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;

/**
 * create time 2025/3/12 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSubquerySQLQueryableFactory implements SubquerySQLQueryableFactory {
    public static final SubquerySQLQueryableFactory INSTANCE = new DefaultSubquerySQLQueryableFactory();

    public <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> SQLQueryable<T1Proxy, T1> create(SubQueryContext<T1Proxy, T1> subQueryContext) {

        EntityExpressionBuilder entityExpressionBuilder = subQueryContext.getEntityExpressionBuilder();
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        TableAvailable leftTable = subQueryContext.getLeftTable();
        String property = subQueryContext.getProperty();
        T1Proxy propertyProxy = subQueryContext.getPropertyProxy();
        String fullName = subQueryContext.getFullName();
        if (leftTable == null || leftTable instanceof EmptyTableAvailable) {
            propertyProxy.setNavValue(fullName);
            return new EmptySQLQueryable<>(subQueryContext.getEntitySQLContext(), propertyProxy);
        }
        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
        RelationTableKey defaultRelationTableKey = new DefaultRelationTableKey(leftTable, property);

        EntityRelationPropertyProvider entityRelationPredicateProvider = navigateMetadata.getEntityRelationPropertyProvider();
        if (subQueryContext.getConfigureExpression() == null && subQueryContext.getOrderByExpression() == null && !subQueryContext.hasElements()) {

            if (entityExpressionBuilder.hasManyJoinConfiguration(defaultRelationTableKey)) {
                ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);
                AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder = EasyRelationalUtil.getManyJoinRelationTable(entityExpressionBuilder, leftTable, navigateMetadata, defaultRelationTableKey, manyConfiguration);

                EntityTableExpressionBuilder manyJoinTableExpressionBuilder = anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder().getTable(0);
                T1Proxy manyJoinPropertyProxy = propertyProxy.create(manyJoinTableExpressionBuilder.getEntityTable(), anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder(), runtimeContext);
                manyJoinPropertyProxy.setNavValue(fullName);
                manyJoinPropertyProxy.getEntitySQLContext().setContextHolder(subQueryContext.getEntitySQLContext().getContextHolder());
                return new EasyManyJoinSQLManyQueryable<>(subQueryContext, anonymousManyJoinEntityTableExpressionBuilder, manyJoinPropertyProxy);
            }
        }

        ClientQueryable<T1> implicitSubQuery = entityRelationPredicateProvider.toImplicitSubQuery(entityExpressionBuilder, leftTable, navigateMetadata, runtimeContext);

        ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);

        if (manyConfiguration != null) {
            implicitSubQuery = EasyObjectUtil.typeCastNullable(manyConfiguration.getConfigureExpression().apply(implicitSubQuery));
        }
        EntityQueryable<T1Proxy, T1> queryable = new EasyEntityQueryable<>(propertyProxy, implicitSubQuery);

        if (subQueryContext.hasElements()) {
            if (subQueryContext.getConfigureExpression() != null) {
                subQueryContext.getConfigureExpression().apply(queryable);
                subQueryContext.setConfigureExpression(null);
            }
            if (subQueryContext.getWhereExpression() != null) {
                queryable.where(subQueryContext.getWhereExpression());
                subQueryContext.setWhereExpression(null);
            }
            if (subQueryContext.getOrderByExpression() != null) {
                queryable.orderBy(subQueryContext.getOrderByExpression());
                subQueryContext.setOrderByExpression(null);
            }
            queryable.limit(subQueryContext.getOffset(), subQueryContext.getLimit());
//            subQueryContext.cleanElements();
//            queryable = queryable.select(s -> s);
        }
        queryable.get1Proxy().setNavValue(fullName);
        queryable.get1Proxy().getEntitySQLContext().setContextHolder(subQueryContext.getEntitySQLContext().getContextHolder());
        return new EasySQLManyQueryable<>(subQueryContext, queryable);
    }

    @Override
    public <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy create(SubQueryContext<T1Proxy, T1> subQueryContext, int index) {

        TableAvailable leftTable = subQueryContext.getLeftTable();
        String property = subQueryContext.getProperty();
        T1Proxy propertyProxy = subQueryContext.getPropertyProxy();
        String fullName = subQueryContext.getFullName();
        //获取导航元信息
        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
        //获取表达式配置信息

        EntityRelationPropertyProvider entityRelationImplicitProvider = navigateMetadata.getEntityRelationPropertyProvider();
        if (entityRelationImplicitProvider instanceof EntityRelationToImplicitPartitionByProvider) {
            AnonymousManyJoinEntityTableExpressionBuilder implicitPartitionBy = ((EntityRelationToImplicitPartitionByProvider) entityRelationImplicitProvider)
                    .toImplicitPartitionBy(propertyProxy.getEntityClass(), subQueryContext.getEntityExpressionBuilder(), leftTable, navigateMetadata, index, subQueryContext.getRuntimeContext(), cq -> {

                        EasyEntityQueryable<T1Proxy, T1> queryable = new EasyEntityQueryable<>(propertyProxy, cq);
                        if (subQueryContext.getWhereExpression() != null) {
                            queryable.where(subQueryContext.getWhereExpression());
                        }
                        if (subQueryContext.getOrderByExpression() != null) {
                            queryable.orderBy(subQueryContext.getOrderByExpression());
                        }
                    });
            T1Proxy t1Proxy = propertyProxy.create(implicitPartitionBy.getEntityTable(), subQueryContext.getEntitySQLContext());
            t1Proxy.getEntitySQLContext().setContextHolder(subQueryContext.getEntitySQLContext().getContextHolder());
            return t1Proxy;
        }

        throw new EasyQueryInvalidOperationException("not support");
    }

}
