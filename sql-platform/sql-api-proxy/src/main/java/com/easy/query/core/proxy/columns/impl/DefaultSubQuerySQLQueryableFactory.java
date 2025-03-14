package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.extension.partition.RowNumberOverBuilder;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.PartitionByRelationTableKey;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.builder.impl.AsSelectorImpl;
import com.easy.query.core.expression.builder.impl.OrderSelectorImpl;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.columns.SubQuerySQLQueryableFactory;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Map;
import java.util.Optional;

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
        RelationTableKey defaultRelationTableKey = new DefaultRelationTableKey(leftTable.getEntityClass(), navigateMetadata.getNavigatePropertyType(), fullName);
        if (subQueryContext.getConfigureExpression() != null || subQueryContext.getOrderByExpression() != null || !subQueryContext.hasElements()) {

            if (entityExpressionBuilder.hasManyJoinConfiguration(defaultRelationTableKey)) {
                ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);
                AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder = EasyRelationalUtil.getManyJoinRelationTable(entityExpressionBuilder, leftTable, navigateMetadata, fullName, Optional.ofNullable(manyConfiguration).map(x -> x.getConfigureExpression()).orElse(null));

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

        EntityExpressionBuilder entityExpressionBuilder = subQueryContext.getEntityExpressionBuilder();
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        TableAvailable leftTable = subQueryContext.getLeftTable();
        String property = subQueryContext.getProperty();
        T1Proxy propertyProxy = subQueryContext.getPropertyProxy();
        String fullName = subQueryContext.getFullName();
        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
        ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(new DefaultRelationTableKey(leftTable.getEntityClass(), navigateMetadata.getNavigatePropertyType(), fullName));
        ClientQueryable<T1> clientQueryable = createSQLKey(subQueryContext, navigateMetadata, manyConfiguration);
        ToSQLResult sqlResult = clientQueryable.toSQLResult();
        String sql = sqlResult.getSQL();
        String parameterString = EasySQLUtil.sqlParameterToString(sqlResult.getSqlContext().getParameters());

        RelationTableKey partitionByRelationTableKey = new PartitionByRelationTableKey(leftTable.getEntityClass(), navigateMetadata.getNavigatePropertyType(), fullName, index, String.format("%s:%s",sql,parameterString));

        AnonymousManyJoinEntityTableExpressionBuilder manySingleJoinRelationTable = EasyRelationalUtil.getManySingleJoinRelationTable(partitionByRelationTableKey, subQueryContext.getEntityExpressionBuilder(), subQueryContext.getLeftTable(), navigateMetadata, fullName, clientQueryable);
        return null;
    }

    private <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> ClientQueryable<T1> createSQLKey(SubQueryContext<T1Proxy, T1> subQueryContext, NavigateMetadata navigateMetadata,ManyConfiguration manyConfiguration) {
        QueryRuntimeContext runtimeContext = subQueryContext.getRuntimeContext();
        T1Proxy propertyProxy = subQueryContext.getPropertyProxy();
        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);

        ClientQueryable<T1> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(propertyProxy.getEntityClass(), runtimeContext);
        SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> queryableSQLFuncExpression1 = Optional.ofNullable(manyConfiguration).map(x -> x.getConfigureExpression()).orElse(null);
        if(queryableSQLFuncExpression1!=null){
            clientQueryable= EasyObjectUtil.typeCastNullable(queryableSQLFuncExpression1.apply(clientQueryable));
        }
        EasyEntityQueryable<T1Proxy, T1> queryable = new EasyEntityQueryable<>(propertyProxy, clientQueryable);
        if (subQueryContext.getWhereExpression() != null) {
            queryable.where(subQueryContext.getWhereExpression());
        }
        if (subQueryContext.getOrderByExpression() != null) {
            queryable.orderBy(subQueryContext.getOrderByExpression());
        }
//        queryable.get1Proxy()

        OrderBySQLBuilderSegment order = clientQueryable.getSQLEntityExpressionBuilder().getOrder();
        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        order.copyTo(orderBySQLBuilderSegment);
        order.clear();

        clientQueryable.select(Map.class,x->{
            x.columnAll();


            PartitionBySQLFunction partitionBySQLFunction = runtimeContext.fx().rowNumberOver(s -> {
                for (String column : targetPropertiesOrPrimary) {
                    s.column(column);
                }
            });
//            runtimeContext.fx().anySQLFunction("{0}",c->{
//            })
//            partitionBySQLFunction.addOrder()
        });

        return clientQueryable;
//        else{
//            clientQueryable.orderByAsc(x->{
//                for (String column : navigateMetadata.getSelfPropertiesOrPrimary()) {
//                    x.column(column);
//                }
//            });
//        }
//        ToSQLResult sqlResult = queryable.toSQLResult();
//        String resultSQL = sqlResult.getSQL();
//        String parameterString = EasySQLUtil.sqlParameterToString(sqlResult.getSqlContext().getParameters());
//
//        StringBuilder sqlKey = new StringBuilder();
//        sqlKey.append("COLUMNS:").append(String.join(",", targetPropertiesOrPrimary));
//        sqlKey.append("SQL:").append(resultSQL).append("PARAMETERS:").append(parameterString);
//        return sqlKey.toString();
    }
}
