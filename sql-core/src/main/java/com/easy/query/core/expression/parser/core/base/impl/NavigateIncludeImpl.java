package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.DirectMappingIterator;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.RelationExtraColumn;
import com.easy.query.core.metadata.RelationExtraMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyOptionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * create time 2023/6/18 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateIncludeImpl implements NavigateInclude {
    private final TableAvailable entityTable;
    private final EntityMetadata entityMetadata;
    private final QueryRuntimeContext runtimeContext;
    private final IncludeNavigateParams includeNavigateParams;
    private final ExpressionContext expressionContext;

    public NavigateIncludeImpl(TableAvailable table, EntityMetadata entityMetadata, QueryRuntimeContext runtimeContext, IncludeNavigateParams includeNavigateParams, ExpressionContext expressionContext) {

        this.entityTable = table;
        this.entityMetadata = entityMetadata;
        this.runtimeContext = runtimeContext;
        this.includeNavigateParams = includeNavigateParams;
        this.expressionContext = expressionContext;
    }

    private void getDirectMappingQueryable(Boolean printSQL, ClientQueryable<?> queryable, DirectMappingIterator directMappingIterator, Integer groupSize, NavigateMetadata propNavigateMetadata) {

        if (directMappingIterator != null) {
            if (directMappingIterator.hasNext()) {
                String prop = directMappingIterator.next();

                queryable.include(navigateInclude -> {

                    ClientQueryable<?> query = navigateInclude.with(prop, groupSize);
                    NavigateMetadata navigateMetadata = navigateInclude.getIncludeNavigateParams().getNavigateMetadata();
                    String nextProp = directMappingIterator.tryGetNextIgnoreBeforeStep();
                    getDirectMappingQueryable(printSQL, query, directMappingIterator, groupSize, navigateMetadata);
                    return getDirectMappingQueryable(printSQL, query, navigateMetadata, nextProp);
                });
            }
        }
//        else {
//
//            queryable.include(navigateInclude -> {
//
//                ClientQueryable<?> query = navigateInclude.with(property, groupSize);
//                NavigateMetadata navigateMetadata = navigateInclude.getIncludeNavigateParams().getNavigateMetadata();
//                return getDirectMappingQueryable(printSQL, queryable, navigateMetadata);
//            });
//        }
    }

    private ClientQueryable<?> getDirectMappingQueryable(Boolean printSQL, ClientQueryable<?> query, NavigateMetadata navigateMetadata, String nextProp) {
        return query
                .configure(s -> {
                    s.setPrintSQL(printSQL);
                    s.setPrintNavSQL(printSQL);
                }).select(z -> {
                    directIncludeSelector(z, navigateMetadata, nextProp);
                });
//        .select(z -> {
//                    String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
//                    for (String targetProperty : targetPropertiesOrPrimary) {
//                        z.column(targetProperty);
//                    }
////                                        z.column(entityNavigateMetadata.getSelfPropertyOrPrimary());
//                    EasySQLExpressionUtil.appendSelfExtraTargetProperty(sqlEntityExpressionBuilder, z.getSQLNative(), z.getTable(), false);
//                    EasySQLExpressionUtil.appendTargetExtraTargetProperty(navigateMetadata, sqlEntityExpressionBuilder, z.getSQLNative(), z.getTable(), false);
//                });
//        .select(query.queryClass(), o -> {
//                    for (String selfMappingProperty : navigateMetadata.getSelfPropertiesOrPrimary()) {
//                        o.column(selfMappingProperty);
//                    }
//                    for (String targetMappingProperty : navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext)) {
//                        o.column(targetMappingProperty);
//                    }
//                });
    }


    private void directIncludeSelector(ColumnSelector<?> columnSelector, NavigateMetadata navigateMetadata, String nextProp) {
        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
        RelationExtraMetadata relationExtraMetadata = columnSelector.getEntityQueryExpressionBuilder().getExpressionContext().getRelationExtraMetadata();
        LinkedHashSet<String> relationColumns = new LinkedHashSet<>(Arrays.asList(targetPropertiesOrPrimary));
        if (nextProp != null) {
            EntityMetadata nextEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType());
            NavigateMetadata nextNavigateMetadata = nextEntityMetadata.getNavigateNotNull(nextProp);
            String[] selfPropertiesOrPrimary = nextNavigateMetadata.getSelfPropertiesOrPrimary();
            relationColumns.addAll(Arrays.asList(selfPropertiesOrPrimary));

        }
        for (String relationColumn : relationColumns) {
            columnSelector.column(relationColumn);

            ColumnMetadata columnMetadata = columnSelector.getEntityMetadata().getColumnNotNull(relationColumn);
            String alias = "__relation__" + relationColumn;
            RelationExtraColumn relationExtraColumn = relationExtraMetadata.getRelationExtraColumnMap().putIfAbsent(alias, new RelationExtraColumn(relationColumn, alias, columnMetadata, false));

        }
    }


    private NavigateMetadata withBefore(String property, Integer groupSize) {

        if (groupSize != null && groupSize < 1) {
            throw new IllegalArgumentException("include group size < 1");
        }
        NavigateMetadata navigateMetadata = entityMetadata.getNavigateNotNull(property);
        includeNavigateParams.setNavigateMetadata(navigateMetadata);
        includeNavigateParams.setTable(this.entityTable);
        if (groupSize == null) {
            includeNavigateParams.setRelationGroupSize(runtimeContext.getQueryConfiguration().getEasyQueryOption().getRelationGroupSize());
        } else {
            includeNavigateParams.setRelationGroupSize(groupSize);
        }
        RelationTypeEnum relationType = navigateMetadata.getRelationType();
        if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping()) && (
                relationType == RelationTypeEnum.ManyToOne
                        ||
                        relationType == RelationTypeEnum.OneToOne
        )) {

            SQLFuncExpression<ClientQueryable<?>> mappingQueryableFunction = () -> {
                DirectMappingIterator directMappingIterator = new DirectMappingIterator(navigateMetadata.getDirectMapping(), 1);
                String prop = directMappingIterator.next();
                NavigateMetadata propNavigateMetadata = navigateMetadata.getEntityMetadata().getNavigateNotNull(prop);

                ClientQueryable<?> mappingQuery = runtimeContext.getSQLClientApiFactory().createQueryable(propNavigateMetadata.getNavigatePropertyType(), runtimeContext);
                Boolean printSQL = EasyOptionUtil.isPrintNavSQL(expressionContext);
                String nextProp = directMappingIterator.tryGetNextIgnoreBeforeStep();
                getDirectMappingQueryable(printSQL, mappingQuery, directMappingIterator, groupSize, propNavigateMetadata);
//                EntityQueryExpressionBuilder sqlEntityExpressionBuilder = mappingQuery.getSQLEntityExpressionBuilder();
                return mappingQuery
                        .configure(s -> {
                            s.setPrintSQL(printSQL);
                            s.setPrintNavSQL(printSQL);
                        }).where(t -> {
                            t.relationIn(navigateMetadata.getDirectMappingSelfPropertiesOrPrimary(runtimeContext), includeNavigateParams.getRelationIds());
                            propNavigateMetadata.predicateFilterApply(t);
                        })
                        .select(z -> {
                            directIncludeSelector(z, propNavigateMetadata, nextProp);
                        });
            };
            includeNavigateParams.setMappingQueryableFunction(mappingQueryableFunction);
        } else if (RelationTypeEnum.ManyToMany == relationType && navigateMetadata.getMappingClass() != null) {//添加多对多中间表
            SQLFuncExpression<ClientQueryable<?>> mappingQueryableFunction = () -> {

                ClientQueryable<?> mappingQuery = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
                Boolean printSQL = EasyOptionUtil.isPrintNavSQL(expressionContext);
                return mappingQuery
                        .configure(s -> {
                            s.setPrintSQL(printSQL);
                            s.setPrintNavSQL(printSQL);
                        }).where(t -> {
                            t.relationIn(navigateMetadata.getSelfMappingProperties(), includeNavigateParams.getRelationIds());
                            navigateMetadata.predicateMappingClassFilterApply(t);
                        })
                        .select(o -> {
                            for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                                o.column(selfMappingProperty);
                            }
                            for (String targetMappingProperty : navigateMetadata.getTargetMappingProperties()) {
                                o.column(targetMappingProperty);
                            }
                        });
            };
            includeNavigateParams.setMappingQueryableFunction(mappingQueryableFunction);
        }
        return navigateMetadata;
    }

    @Override
    public IncludeNavigateParams getIncludeNavigateParams() {
        return includeNavigateParams;
    }

    @Override
    public <TREntity> ClientQueryable<TREntity> with(String property, Integer groupSize) {
        NavigateMetadata navigateMetadata = withBefore(property, groupSize);
        Class<?> navigatePropertyType = navigateMetadata.getNavigatePropertyType();
        boolean tracking = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.USE_TRACKING);
        ClientQueryable<TREntity> queryable = runtimeContext.getSQLClientApiFactory().createQueryable(EasyObjectUtil.typeCastNullable(navigatePropertyType), runtimeContext);

        Boolean printNavSQL = EasyOptionUtil.isPrintNavSQL(expressionContext);
        //支持tracking传递
        if (tracking) {
            queryable.getSQLEntityExpressionBuilder().getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.USE_TRACKING);
        } else {
            queryable.getSQLEntityExpressionBuilder().getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.USE_TRACKING);
        }
        //支持interceptor传递
        if (!expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.USE_INTERCEPTOR)) {
            queryable.getSQLEntityExpressionBuilder().getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
        }
        expressionContext.getExpressionContextInterceptor().copyTo(queryable.getSQLEntityExpressionBuilder().getExpressionContext().getExpressionContextInterceptor());


        return queryable.configure(s -> {
            s.setPrintSQL(printNavSQL);
            s.setPrintNavSQL(printNavSQL);
        });
//        return queryable.where(o->{
//            if(includeNavigateParams.isLimit()){
//                o.eq(navigateMetadata.getTargetPropertyOrPrimary(runtimeContext),includeNavigateParams.getRelationId());
//            }else{
//                o.in(navigateMetadata.getTargetPropertyOrPrimary(runtimeContext),includeNavigateParams.getRelationIds());
//            }
//        });
    }
}
