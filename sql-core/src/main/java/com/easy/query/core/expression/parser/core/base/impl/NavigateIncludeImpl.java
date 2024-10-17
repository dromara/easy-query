package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.MultiCollectionImpl;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyOptionUtil;

/**
 * create time 2023/6/18 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateIncludeImpl<TEntity> implements NavigateInclude<TEntity> {
    private final TableAvailable entityTable;
    private final QueryRuntimeContext runtimeContext;
    private final IncludeNavigateParams includeNavigateParams;
    private final ExpressionContext expressionContext;

    public NavigateIncludeImpl(TableAvailable table, QueryRuntimeContext runtimeContext, IncludeNavigateParams includeNavigateParams, ExpressionContext expressionContext) {

        this.entityTable = table;
        this.runtimeContext = runtimeContext;
        this.includeNavigateParams = includeNavigateParams;
        this.expressionContext = expressionContext;
    }

    private NavigateMetadata withBefore(String property, Integer groupSize) {

        if (groupSize != null && groupSize < 1) {
            throw new IllegalArgumentException("include group size < 1");
        }
        NavigateMetadata navigateMetadata = entityTable.getEntityMetadata().getNavigateNotNull(property);
        includeNavigateParams.setNavigateMetadata(navigateMetadata);
        includeNavigateParams.setTable(this.entityTable);
        if (groupSize == null) {
            includeNavigateParams.setRelationGroupSize(runtimeContext.getQueryConfiguration().getEasyQueryOption().getRelationGroupSize());
        } else {
            includeNavigateParams.setRelationGroupSize(groupSize);
        }
        RelationTypeEnum relationType = navigateMetadata.getRelationType();
        //添加多对多中间表
        if (RelationTypeEnum.ManyToMany == relationType && navigateMetadata.getMappingClass() != null) {
            ClientQueryable<?> mappingQuery = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
            Boolean printSQL = EasyOptionUtil.isPrintNavSQL(expressionContext);
            ClientQueryable<?> mappingQueryable = mappingQuery
                    .configure(s -> {
                        s.setPrintSQL(printSQL);
                        s.setPrintNavSQL(printSQL);
                    }).where(t -> {
                        t.relationIn( navigateMetadata.getSelfMappingProperties(), () -> includeNavigateParams.getRelationIds());
                        navigateMetadata.predicateFilterApply(t);
                    })
                    .select(o -> {
                        for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                            o.column(selfMappingProperty);
                        }
                        for (String targetMappingProperty : navigateMetadata.getTargetMappingProperties()) {
                            o.column(targetMappingProperty);
                        }
                    });
            includeNavigateParams.setMappingQueryable(mappingQueryable);
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
