package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.DirectMappingIterator;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationEntityTableAvailable;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.builder.impl.DefaultTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.Arrays;
import java.util.Map;

/**
 * create time 2024/6/7 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyRelationalUtil {
//    public static TableAvailable getRelationTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, String fullName) {
//        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
//        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
//        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToOne && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToOne) {
//            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToOne or ManyToOne],now is " + navigateMetadata.getRelationType());
//        }
//        Class<?> navigateEntityClass = navigateMetadata.getNavigatePropertyType();
//        EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(new RelationTableKey(leftTable.getEntityClass(), navigateEntityClass, fullName), key -> {
//            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateEntityClass);

    /// /            TableAvailable leftTable = getTable();
//            RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, entityMetadata, false);
//            boolean query = entityExpressionBuilder.isQuery();
//            EntityTableExpressionBuilder tableExpressionBuilder = new DefaultTableExpressionBuilder(rightTable, query ? MultiTableTypeEnum.LEFT_JOIN : MultiTableTypeEnum.INNER_JOIN, runtimeContext);
//            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
//
//            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
//            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);
//            sqlPredicate.and(() -> {
//                sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
//                if (navigateMetadata.hasPredicateFilterExpression()) {
//                    navigateMetadata.predicateFilterApply(sqlPredicate);
//                }
//            });
//            tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
//            return tableExpressionBuilder;
//        });
//        return entityTableExpressionBuilder.getEntityTable();
//    }

    /**
     * 用来处理动态属性的多级属性问题
     * 根据属性和主表获取关联对象属性表,比如user表有name属性和address关联表然后address又有city属性
     * property为[name]等于表达式[o.name()]
     * property为[address.city]等于表达式[o.address().city()]
     * 返回表对象和最终一级的属性比如city
     *
     * @param entityExpressionBuilder
     * @param leftTable
     * @param property
     * @return
     */
    public static TableOrRelationTable getTableOrRelationalTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property) {
        return getTableOrRelationalTable(entityExpressionBuilder, leftTable, property, true);
    }

    public static TableOrRelationTable getTableOrRelationalTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, boolean strictMode) {
        if (property.contains(".")) {
            String[] properties = property.split("\\.");
            return getTableOrRelationalTable(entityExpressionBuilder, leftTable, properties, strictMode);
        } else {
            return new TableOrRelationTable(leftTable, property);
        }
    }

    public static TableOrRelationTable getTableOrRelationalTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String[] properties, boolean strictMode) {
        if (EasyArrayUtil.isEmpty(properties)) {
            throw new IllegalArgumentException("properties is empty");
        }
        if (properties.length > 1) {
            TableAvailable relationTable = leftTable;
            boolean skip = false;
            StringBuilder fullName = new StringBuilder();
            for (int i = 0; i < properties.length - 1 && !skip; i++) {
                String navigateEntityProperty = properties[i];
                fullName.append(navigateEntityProperty).append(".");
                relationTable = EasyRelationalUtil.getRelationTable(entityExpressionBuilder, relationTable, navigateEntityProperty, fullName.substring(0, fullName.length() - 1), strictMode);
                if (relationTable == null) {
                    skip = true;
                }
            }

            return new TableOrRelationTable(relationTable, properties[properties.length - 1]);
        } else {
            return new TableOrRelationTable(leftTable, properties[0]);
        }
    }

    public static TableAvailable getRelationTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, String fullName) {
        return getRelationTable(entityExpressionBuilder, leftTable, property, fullName, true);
    }

    public static TableAvailable getRelationTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, String fullName, boolean strictMode) {
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();

        NavigateMetadata navigateMetadata = strictMode ? leftTable.getEntityMetadata().getNavigateNotNull(property) : leftTable.getEntityMetadata().getNavigateOrNull(property);
        if (navigateMetadata == null) {
            return null;
        }
        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToOne && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToOne) {
            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToOne or ManyToOne],now is " + navigateMetadata.getRelationType());
        }
        String[] directMapping = navigateMetadata.getDirectMapping();
        if (EasyArrayUtil.isNotEmpty(directMapping)) {
            TableAvailable myLeftTable = leftTable;
            DirectMappingIterator directMappingIterator = new DirectMappingIterator(directMapping);
            while (directMappingIterator.hasNext()) {
                String prop = directMappingIterator.next();
                myLeftTable = getRelationTable(entityExpressionBuilder, myLeftTable, prop, directMappingIterator.getFullName());
            }
            return myLeftTable;
        } else {

            Class<?> navigateEntityClass = navigateMetadata.getNavigatePropertyType();
            EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(new DefaultRelationTableKey(leftTable,navigateMetadata.getPropertyName()), key -> {
                EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateEntityClass);
//            TableAvailable leftTable = getTable();
                RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, entityMetadata, false);
                boolean query = entityExpressionBuilder.isQuery();
                EntityTableExpressionBuilder tableExpressionBuilder = new DefaultTableExpressionBuilder(rightTable, query ? MultiTableTypeEnum.LEFT_JOIN : MultiTableTypeEnum.INNER_JOIN, runtimeContext);
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment();

                SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
                WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);
                sqlPredicate.and(() -> {
                    sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                    if (navigateMetadata.hasPredicateFilterExpression()) {
                        navigateMetadata.predicateFilterApply(sqlPredicate);
                    }
                });
                tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
                return tableExpressionBuilder;
            });
            return entityTableExpressionBuilder.getEntityTable();
        }

    }

    public static AnonymousManyJoinEntityTableExpressionBuilder getManyJoinRelationTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata,ManyConfiguration manyConfiguration) {
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();

        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToMany && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToMany or ManyToMany],now is " + navigateMetadata.getRelationType());
        }

        EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(new DefaultRelationTableKey(leftTable,navigateMetadata.getPropertyName()), key -> {
//            TableAvailable leftTable = getTable();

            String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(Map.class);
            ClientQueryable<?> manyQueryable = createManyQueryable(runtimeContext, navigateMetadata, targetPropertiesOrPrimary, manyConfiguration);
            RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, entityMetadata, true);
            entityExpressionBuilder.getExpressionContext().extract(manyQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
            ExpressionBuilderFactory expressionBuilderFactory = runtimeContext.getExpressionBuilderFactory();
            EntityTableExpressionBuilder tableExpressionBuilder = expressionBuilderFactory.createAnonymousManyGroupEntityTableExpressionBuilder(rightTable, MultiTableTypeEnum.LEFT_JOIN, manyQueryable.getSQLEntityExpressionBuilder(), targetPropertiesOrPrimary);

            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();

            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);

            if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {

                sqlPredicate.and(() -> {
                    EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());
                    String[] selfMappingColumnNames = Arrays.stream(navigateMetadata.getSelfMappingProperties()).map(prop -> mappingEntityMetadata.getColumnNotNull(prop).getName()).toArray(String[]::new);

                    for (int i = 0; i < selfMappingColumnNames.length; i++) {
                        String selfMappingColumnName = selfMappingColumnNames[i];
                        String selfProperty = navigateMetadata.getSelfPropertiesOrPrimary()[i];
                        sqlPredicate.sqlNativeSegment("{0} = {1}", c -> {
                            c.columnName(selfMappingColumnName);
                            c.expression(leftTable, selfProperty);
                        });
                    }
                });
            } else {
                sqlPredicate.and(() -> {
                    sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), targetPropertiesOrPrimary, navigateMetadata.getSelfPropertiesOrPrimary());
                });
            }
            tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
            return tableExpressionBuilder;
        });
        return ((AnonymousManyJoinEntityTableExpressionBuilder) entityTableExpressionBuilder);
    }

    public static AnonymousManyJoinEntityTableExpressionBuilder getManySingleJoinRelationTable(RelationTableKey relationTableKey
            , EntityExpressionBuilder entityExpressionBuilder
            , TableAvailable leftTable
            , NavigateMetadata navigateMetadata
            , int index
            , ClientQueryable<?> clientQueryable) {
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();

        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToMany && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToMany or ManyToMany],now is " + navigateMetadata.getRelationType());
        }
        EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(relationTableKey, key -> {
//            TableAvailable leftTable = getTable();

            EntityMetadata partitionByEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType());

            String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
            ClientQueryable<?> select = clientQueryable.where(m -> m.eq("__row__", index + 1))
                    .select(navigateMetadata.getNavigatePropertyType(), o -> {
                        for (Map.Entry<String, ColumnMetadata> columnMetadataEntry : partitionByEntityMetadata.getProperty2ColumnMap().entrySet()) {
//                            o.column(columnMetadataEntry.getValue().getName());
                            o.sqlNativeSegment("{0}", c -> {
                                c.expression(columnMetadataEntry.getValue().getName());
                                c.setAlias(columnMetadataEntry.getValue().getName());
                            });
                        }
                        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
                            EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());
                            for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                                ColumnMetadata columnMetadata = mappingEntityMetadata.getColumnNotNull(selfMappingProperty);
//                                o.columnAs(columnMetadata.getName(), columnMetadata.getName());
                                o.sqlNativeSegment("{0}", c -> {
                                    c.expression(columnMetadata.getName());
                                    c.setAlias(columnMetadata.getName());
                                });
                            }
                        }
                    });

            RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, partitionByEntityMetadata, true);
            entityExpressionBuilder.getExpressionContext().extract(select.getSQLEntityExpressionBuilder().getExpressionContext());
            ExpressionBuilderFactory expressionBuilderFactory = runtimeContext.getExpressionBuilderFactory();
            EntityTableExpressionBuilder tableExpressionBuilder = expressionBuilderFactory.createAnonymousManyGroupEntityTableExpressionBuilder(rightTable, MultiTableTypeEnum.LEFT_JOIN, clientQueryable.getSQLEntityExpressionBuilder(), targetPropertiesOrPrimary);

            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();

            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);


            if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {

                EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());
                String[] selfMappingColumnNames = Arrays.stream(navigateMetadata.getSelfMappingProperties()).map(prop -> mappingEntityMetadata.getColumnNotNull(prop).getName()).toArray(String[]::new);
                sqlPredicate.and(() -> {
                    for (int i = 0; i < selfMappingColumnNames.length; i++) {
                        String selfMappingColumnName = selfMappingColumnNames[i];
                        String selfProperty = navigateMetadata.getSelfPropertiesOrPrimary()[i];
                        sqlPredicate.sqlNativeSegment("{0} = {1}", c -> {
                            c.columnName(selfMappingColumnName);
                            c.expression(leftTable, selfProperty);
                        });
                    }
//                    sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), selfMappingColumnNames, navigateMetadata.getSelfPropertiesOrPrimary());
                });
            } else {
                sqlPredicate.and(() -> {
                    sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), targetPropertiesOrPrimary, navigateMetadata.getSelfPropertiesOrPrimary());
                });
            }
//            sqlPredicate.and(() -> {
//                sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), targetPropertiesOrPrimary, navigateMetadata.getSelfPropertiesOrPrimary());
//            });
            tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
            return tableExpressionBuilder;
        });
        return ((AnonymousManyJoinEntityTableExpressionBuilder) entityTableExpressionBuilder);
    }


    private static ClientQueryable<?> createManyQueryable(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertiesOrPrimary, ManyConfiguration manyConfiguration) {

        ClientQueryable<?> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getNavigatePropertyType(), runtimeContext);
        if (manyConfiguration != null) {
            SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> configureExpression = manyConfiguration.getConfigureExpression();
            if(configureExpression!=null){
                clientQueryable = configureExpression.apply(clientQueryable);
            }
        }

        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            clientQueryable.innerJoin(navigateMetadata.getMappingClass(), (target, middle) -> {
                        target.multiEq(true, middle, targetPropertiesOrPrimary, navigateMetadata.getTargetMappingProperties());
                    })
                    .where((target, middle) -> {
                        navigateMetadata.predicateMappingClassFilterApply(middle);
                        navigateMetadata.predicateFilterApply(target);
                    }).groupBy((target, middle) -> {
                        for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                            middle.column(selfMappingProperty);
                        }
                    }).select(Map.class, (target, middle) -> {
                        EntityMetadata middleEntityMetadata = middle.getEntityMetadata();
                        for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                            ColumnMetadata columnMetadata = middleEntityMetadata.getColumnNotNull(selfMappingProperty);
                            middle.columnAs(selfMappingProperty, columnMetadata.getName());
                        }
                    });
            return clientQueryable;
        } else {
            return clientQueryable.where(t -> {
                navigateMetadata.predicateFilterApply(t);
            }).groupBy(o -> {
                for (String column : targetPropertiesOrPrimary) {
                    o.column(column);
                }
            }).select(o -> {
                for (String column : targetPropertiesOrPrimary) {
                    o.columnFixedAs(column, column);
                }
            });
        }
    }


//    public static AnonymousManyJoinEntityTableExpressionBuilder getGroupPartitionByRelationTable(RelationTableKey relationTableKey
//            , EntityExpressionBuilder entityExpressionBuilder
//            , TableAvailable leftTable
//            , NavigateMetadata navigateMetadata
//            , int index
//            , ClientQueryable<?> clientQueryable) {
//        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
//
//        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToMany && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
//            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToMany or ManyToMany],now is " + navigateMetadata.getRelationType());
//        }
//        EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(relationTableKey, key -> {
////            TableAvailable leftTable = getTable();
//
//            EntityMetadata partitionByEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(leftTable.getEntityClass());
//
//            String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
//            ClientQueryable<?> select = clientQueryable.where(m -> m.eq("__row__", index + 1))
//                    .select(navigateMetadata.getNavigatePropertyType(), o -> {
//                        for (Map.Entry<String, ColumnMetadata> columnMetadataEntry : partitionByEntityMetadata.getProperty2ColumnMap().entrySet()) {

    /// /                            o.column(columnMetadataEntry.getValue().getName());
//                            o.sqlNativeSegment("{0}",c->{
//                                c.expression(columnMetadataEntry.getValue().getName());
//                                c.setAlias(columnMetadataEntry.getValue().getName());
//                            });
//                        }
//                    });
//
//            RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, partitionByEntityMetadata, true);
//            entityExpressionBuilder.getExpressionContext().extract(select.getSQLEntityExpressionBuilder().getExpressionContext());
//            ExpressionBuilderFactory expressionBuilderFactory = runtimeContext.getExpressionBuilderFactory();
//            EntityTableExpressionBuilder tableExpressionBuilder = expressionBuilderFactory.createAnonymousManyGroupEntityTableExpressionBuilder(rightTable, MultiTableTypeEnum.LEFT_JOIN, clientQueryable.getSQLEntityExpressionBuilder(), targetPropertiesOrPrimary);
//
//            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
//
//            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
//            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);
//
//            sqlPredicate.and(() -> {
//                sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), targetPropertiesOrPrimary, navigateMetadata.getSelfPropertiesOrPrimary());
//            });
//            tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
//            return tableExpressionBuilder;
//        });
//        return ((AnonymousManyJoinEntityTableExpressionBuilder) entityTableExpressionBuilder);
//    }

    public static class TableOrRelationTable {
        public final TableAvailable table;
        public final String property;

        public TableOrRelationTable(TableAvailable table, String property) {
            this.table = table;
            this.property = property;
        }
    }
}
