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

    @Override
    public <TProperty> ClientQueryable<TProperty> with(String property) {
        NavigateMetadata navigateMetadata = entityTable.getEntityMetadata().getNavigateNotNull(property);
        includeNavigateParams.setNavigateMetadata(navigateMetadata);
        Class<?> navigatePropertyType = navigateMetadata.getNavigatePropertyType();
        ClientQueryable<TProperty> queryable = runtimeContext.getSQLClientApiFactory().createQueryable(EasyObjectUtil.typeCastNullable(navigatePropertyType), runtimeContext);
        RelationTypeEnum relationType = navigateMetadata.getRelationType();
        //添加多对多中间表
        if(RelationTypeEnum.ManyToMany==relationType){
            ClientQueryable<?> mappingQuery = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
            ClientQueryable<?> mappingQueryable = mappingQuery.where(t -> t.in(navigateMetadata.getSelfMappingProperty(), includeNavigateParams.getRelationIds()))
                    .select(o -> o.column(navigateMetadata.getSelfMappingProperty()).column(navigateMetadata.getTargetMappingProperty()));
            includeNavigateParams.setMappingQueryable(mappingQueryable);
        }
        return queryable.where(o->o.in(navigateMetadata.getTargetPropertyOrPrimary(runtimeContext),includeNavigateParams.getRelationIds()));
    }
}
