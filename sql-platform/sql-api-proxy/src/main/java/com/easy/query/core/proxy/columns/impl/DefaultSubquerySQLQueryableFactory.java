package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.PartitionByRelationTableKey;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.EmptyTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.segment.builder.GroupBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.columns.SubquerySQLQueryableFactory;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Map;
import java.util.Optional;

/**
 * create time 2025/3/12 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSubquerySQLQueryableFactory implements SubquerySQLQueryableFactory {
    public static final SubquerySQLQueryableFactory INSTANCE = new DefaultSubquerySQLQueryableFactory();

    public <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> SQLQueryable<T1Proxy, T1> create(SubQueryContext<T1Proxy, T1> subqueryContext) {

        EntityExpressionBuilder entityExpressionBuilder = subqueryContext.getEntityExpressionBuilder();
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        TableAvailable leftTable = subqueryContext.getLeftTable();
        String property = subqueryContext.getProperty();
        T1Proxy propertyProxy = subqueryContext.getPropertyProxy();
        String fullName = subqueryContext.getFullName();
        if (leftTable == null || leftTable instanceof EmptyTableAvailable) {
            propertyProxy.setNavValue(fullName);
            return new EmptySQLQueryable<>(subqueryContext.getEntitySQLContext(), propertyProxy);
        }
        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
        RelationTableKey defaultRelationTableKey = new DefaultRelationTableKey(leftTable,property);
        if (subqueryContext.getConfigureExpression() == null && subqueryContext.getOrderByExpression() == null && !subqueryContext.hasElements()) {

            if (entityExpressionBuilder.hasManyJoinConfiguration(defaultRelationTableKey)) {
                ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);
                AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder = EasyRelationalUtil.getManyJoinRelationTable(entityExpressionBuilder, leftTable, navigateMetadata, Optional.ofNullable(manyConfiguration).map(x -> x.getConfigureExpression()).orElse(null));

                EntityTableExpressionBuilder manyJoinTableExpressionBuilder = anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder().getTable(0);
                T1Proxy manyJoinPropertyProxy = propertyProxy.create(manyJoinTableExpressionBuilder.getEntityTable(), anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder(), runtimeContext);
                manyJoinPropertyProxy.setNavValue(fullName);
                return new EasyManyJoinSQLManyQueryable<>(subqueryContext, anonymousManyJoinEntityTableExpressionBuilder, manyJoinPropertyProxy);
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
        EasySQLManyQueryable<T1Proxy, T1> query = new EasySQLManyQueryable<>(subqueryContext, queryable);
        return query;
    }

    @Override
    public <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy create(SubQueryContext<T1Proxy, T1> subqueryContext, int index) {

        EntityExpressionBuilder entityExpressionBuilder = subqueryContext.getEntityExpressionBuilder();
        TableAvailable leftTable = subqueryContext.getLeftTable();
        String property = subqueryContext.getProperty();
        T1Proxy propertyProxy = subqueryContext.getPropertyProxy();
        String fullName = subqueryContext.getFullName();
        //获取导航元信息
        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
        //获取表达式配置信息
        ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(new DefaultRelationTableKey(leftTable,property));
        //创建分区分组查询表达式
        ClientQueryable<?> clientQueryable = createPartitionQueryable(subqueryContext, navigateMetadata, manyConfiguration);
        ToSQLResult sqlResult = clientQueryable.toSQLResult();
        String sql = sqlResult.getSQL();
        //后续SQLParameter改成实现hashCode和equals
        String parameterString = EasySQLUtil.sqlParameterToString(sqlResult.getSqlContext().getParameters());

        RelationTableKey partitionByRelationTableKey = new PartitionByRelationTableKey(leftTable.getEntityClass(), navigateMetadata.getNavigatePropertyType(), fullName, index, String.format("%s:%s", sql, parameterString));

        AnonymousManyJoinEntityTableExpressionBuilder manySingleJoinRelationTable = EasyRelationalUtil.getManySingleJoinRelationTable(partitionByRelationTableKey, subqueryContext.getEntityExpressionBuilder(), subqueryContext.getLeftTable(), navigateMetadata, index, clientQueryable);
        return propertyProxy.create(manySingleJoinRelationTable.getEntityTable(), subqueryContext.getEntitySQLContext());
    }

    private <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> ClientQueryable<?> createPartitionQueryable(SubQueryContext<T1Proxy, T1> subQueryContext, NavigateMetadata navigateMetadata, ManyConfiguration manyConfiguration) {
        QueryRuntimeContext runtimeContext = subQueryContext.getRuntimeContext();
        T1Proxy propertyProxy = subQueryContext.getPropertyProxy();
        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);

        ClientQueryable<T1> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(propertyProxy.getEntityClass(), runtimeContext);
        SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> queryableSQLFuncExpression1 = Optional.ofNullable(manyConfiguration).map(x -> x.getConfigureExpression()).orElse(null);
        if (queryableSQLFuncExpression1 != null) {
            clientQueryable = EasyObjectUtil.typeCastNullable(queryableSQLFuncExpression1.apply(clientQueryable));
        }


        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            clientQueryable.innerJoin(navigateMetadata.getMappingClass(), (target, middle) -> {
                        target.multiEq(true, middle, navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getTargetMappingProperties());
                    })
                    .where((target, middle) -> {
                        navigateMetadata.predicateMappingClassFilterApply(middle);
                        navigateMetadata.predicateFilterApply(target);
                    }).select(Map.class, (target, middle) -> {
                        EntityMetadata middleEntityMetadata = middle.getEntityMetadata();
                        for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                            ColumnMetadata columnMetadata = middleEntityMetadata.getColumnNotNull(selfMappingProperty);
                            middle.columnAs(selfMappingProperty, columnMetadata.getName());
                        }
                    });
        } else {
            clientQueryable.where(t -> {
                navigateMetadata.predicateFilterApply(t);

            });
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

        return clientQueryable.select(Map.class, x -> {
            x.columnAll();


            PartitionBySQLFunction partitionBySQLFunction = runtimeContext.fx().rowNumberOver(s -> {
                for (String column : targetPropertiesOrPrimary) {
                    s.column(column);
                }
            });
            partitionBySQLFunction.addOrder(orderBySQLBuilderSegment);
            x.sqlFuncAs(partitionBySQLFunction, "__row__");

        });
    }

//    @Override
//    public <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy create(GroupingQueryContext<T1Proxy, T1> groupingQueryContext, int index) {
//        EntityExpressionBuilder entityExpressionBuilder = groupingQueryContext.getEntityExpressionBuilder();
//        TableAvailable leftTable = groupingQueryContext.getLeftTable();
//        T1Proxy propertyProxy = groupingQueryContext.getPropertyProxy();
//        ClientQueryable<?> clientQueryable = createPartitionQueryable(groupingQueryContext);
//
//        RelationTableKey partitionByRelationTableKey = new PartitionByRelationTableKey(leftTable.getEntityClass(), leftTable.getEntityClass(), "__self__", index, "__group_partition__");
//
//        AnonymousManyJoinEntityTableExpressionBuilder manySingleJoinRelationTable = EasyRelationalUtil.getManySingleJoinRelationTable(partitionByRelationTableKey, groupingQueryContext.getEntityExpressionBuilder(), groupingQueryContext.getLeftTable(), navigateMetadata, index, clientQueryable);
//        return propertyProxy.create(manySingleJoinRelationTable.getEntityTable(), groupingQueryContext.getEntitySQLContext());
//    }


//    private <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> ClientQueryable<?> createPartitionQueryable(GroupingQueryContext<T1Proxy, T1> groupingQueryContext) {
//        QueryRuntimeContext runtimeContext = groupingQueryContext.getRuntimeContext();
//        T1Proxy propertyProxy = groupingQueryContext.getPropertyProxy();
//
//        ClientQueryable<T1> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(propertyProxy.getEntityClass(), runtimeContext);
//
//
//        EasyEntityQueryable<T1Proxy, T1> queryable = new EasyEntityQueryable<>(propertyProxy, clientQueryable);
//        if (groupingQueryContext.getWhereExpression() != null) {
//            queryable.where(groupingQueryContext.getWhereExpression());
//        }
//        if (groupingQueryContext.getOrderByExpression() != null) {
//            queryable.orderBy(groupingQueryContext.getOrderByExpression());
//        }
////        queryable.get1Proxy()
//
//        OrderBySQLBuilderSegment order = clientQueryable.getSQLEntityExpressionBuilder().getOrder();
//        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
//        order.copyTo(orderBySQLBuilderSegment);
//        order.clear();
//        SQLBuilderSegment group = clientQueryable.getSQLEntityExpressionBuilder().getGroup();
//        GroupBySQLBuilderSegmentImpl groupBySQLBuilderSegment = new GroupBySQLBuilderSegmentImpl();
//        group.copyTo(groupBySQLBuilderSegment);
//
//        return clientQueryable.select(Map.class, x -> {
//            x.columnAll();
//
//
//            PartitionBySQLFunction partitionBySQLFunction = runtimeContext.fx().rowNumberOver(s -> {
//                s.sql(groupBySQLBuilderSegment);
//            });
//            partitionBySQLFunction.addOrder(orderBySQLBuilderSegment);
//            x.sqlFuncAs(partitionBySQLFunction, "__row__");
//
//        });
//    }
}
