package com.easy.query.core.expression.implicit;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.include.getter.RelationIncludeGetter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.Collection;
import java.util.List;

/**
 * create time 2025/3/19 16:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityRelationPropertyProvider {
    String getName();

    /**
     * 隐式子查询
     * @param entityExpressionBuilder
     * @param leftTable
     * @param navigateMetadata
     * @param runtimeContext
     * @return
     * @param <T>
     */
    <T> ClientQueryable<T> toImplicitSubQuery(EntityExpressionBuilder entityExpressionBuilder,TableAvailable leftTable, NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext);

    /**
     * 隐式join
     * @param entityExpressionBuilder
     * @param leftTable
     * @param property
     * @param fullName
     * @return
     */
    TableAvailable toImplicitJoin(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, String fullName);

    /**
     * 拉取的条件
     * @param targetWherePredicate
     * @param targetProps
     * @param relationIds
     */
    void relationMultiIdsFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<List<Object>> relationIds);

    /**
     * 拉取带limit的条件
     * @param targetWherePredicate
     * @param targetProps
     * @param relationIds
     */
    void relationMultiIdFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<Object> relationIds);


    RelationIncludeGetter getOneToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] selfRelationColumn, Collection<RelationExtraEntity> entities);
    RelationIncludeGetter getDirectToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, List<RelationExtraEntity> includes, List<Object> mappingRow);
    RelationIncludeGetter getManyToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes);
    RelationIncludeGetter getOneToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes);
    RelationIncludeGetter getManyToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes, List<Object> mappingRows);
}
