package com.easy.query.mysql.config;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.include.getter.RelationIncludeGetter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.List;

/**
 * create time 2025/3/19 16:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class FindInSetRelationToImplicitProvider implements EntityRelationPropertyProvider {
    public static final EntityRelationPropertyProvider INSTANCE = new FindInSetRelationToImplicitProvider();


    @Override
    public String getName() {
        return "find_in_set";
    }

    @Override
    public <T> ClientQueryable<T> toImplicitSubQuery(TableAvailable leftTable, NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext) {

        ClientQueryable<?> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getNavigatePropertyType(), runtimeContext);
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            throw new UnsupportedOperationException("many to many not support find_in_set");
        } else {
            clientQueryable.where(t -> {
                String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
                String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
                t.and(() -> {
                    for (int i = 0; i < targetPropertiesOrPrimary.length; i++) {
                        String property = targetPropertiesOrPrimary[i];
                        String selfProperty = selfPropertiesOrPrimary[i];
                        t.sqlNativeSegment("FIND_IN_SET({0},{1})", c -> {
                            c.expression(t.getTable(), property)
                                    .expression(leftTable, selfProperty);
                        });
                    }
//                    t.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                    navigateMetadata.predicateFilterApply(t);
                });
            });
        }
        return EasyObjectUtil.typeCastNullable(clientQueryable);
    }

    @Override
    public TableAvailable toImplicitJoin(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, String fullName) {
        return null;
    }

    @Override
    public void relationMultiIdsFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<List<Object>> relationIds) {

    }

    @Override
    public void relationMultiIdFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<Object> relationIds) {

    }

    @Override
    public RelationIncludeGetter getOneToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] selfRelationColumn, Collection<RelationExtraEntity> entities) {
        return null;
    }

    @Override
    public RelationIncludeGetter getDirectToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, List<RelationExtraEntity> includes, List<Object> mappingRow) {
        return null;
    }

    @Override
    public RelationIncludeGetter getManyToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        return null;
    }

    @Override
    public RelationIncludeGetter getOneToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        return null;
    }

    @Override
    public RelationIncludeGetter getManyToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes, List<Object> mappingRows) {
        return null;
    }
}
