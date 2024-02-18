package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyObjectUtil;

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

    public NavigateIncludeImpl(TableAvailable table, QueryRuntimeContext runtimeContext, IncludeNavigateParams includeNavigateParams) {

        this.entityTable = table;
        this.runtimeContext = runtimeContext;
        this.includeNavigateParams = includeNavigateParams;
    }
    private NavigateMetadata withBefore(String property, Integer groupSize){

        if(groupSize!=null&&groupSize<1){
            throw new IllegalArgumentException("include group size < 1");
        }
        NavigateMetadata navigateMetadata = entityTable.getEntityMetadata().getNavigateNotNull(property);
        includeNavigateParams.setNavigateMetadata(navigateMetadata);
        includeNavigateParams.setTable(this.entityTable);
        includeNavigateParams.setRelationGroupSize(groupSize);
        RelationTypeEnum relationType = navigateMetadata.getRelationType();
        //添加多对多中间表
        if(RelationTypeEnum.ManyToMany==relationType){
            ClientQueryable<?> mappingQuery = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
            ClientQueryable<?> mappingQueryable = mappingQuery.where(t -> t.in(navigateMetadata.getSelfMappingProperty(), includeNavigateParams.getRelationIds()))
                    .select(o -> o.column(navigateMetadata.getSelfMappingProperty()).column(navigateMetadata.getTargetMappingProperty()));
            includeNavigateParams.setMappingQueryable(mappingQueryable);
        }
        return navigateMetadata;
    }

    @Override
    public IncludeNavigateParams getIncludeNavigateParams() {
        return includeNavigateParams;
    }

    @Override
    public <TREntity> ClientQueryable<TREntity> with(String property,Integer groupSize) {
        NavigateMetadata navigateMetadata = withBefore(property, groupSize);
        Class<?> navigatePropertyType = navigateMetadata.getNavigatePropertyType();
        return runtimeContext.getSQLClientApiFactory().createQueryable(EasyObjectUtil.typeCastNullable(navigatePropertyType), runtimeContext);

//        return queryable.where(o->{
//            if(includeNavigateParams.isLimit()){
//                o.eq(navigateMetadata.getTargetPropertyOrPrimary(runtimeContext),includeNavigateParams.getRelationId());
//            }else{
//                o.in(navigateMetadata.getTargetPropertyOrPrimary(runtimeContext),includeNavigateParams.getRelationIds());
//            }
//        });
    }
}
