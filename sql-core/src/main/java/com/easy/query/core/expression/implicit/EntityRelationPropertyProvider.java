package com.easy.query.core.expression.implicit;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.include.getter.RelationIncludeGetter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.metadata.IncludeNavigateParams;
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
     * @return
     */
    TableAvailable toImplicitJoin(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property);

    /**
     * 拉取时候的条件
     * @param targetWherePredicate
     * @param targetProps
     * @param relationIds
     * @param groupSize
     */
    void relationMultiIdsFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<List<Object>> relationIds, Integer groupSize);

    /**
     * 拉取带limit时候的条件
     * @param targetWherePredicate
     * @param targetProps
     * @param relationIds
     */
    void relationMultiIdFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<Object> relationIds);

    /**
     * 一对一处理时遍历includes然后赋值给entities
     * @param runtimeContext
     * @param navigateMetadata
     * @param selfRelationColumn
     * @param entities
     * @return
     */
    RelationIncludeGetter getOneToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] selfRelationColumn, Collection<RelationExtraEntity> entities);

    /**
     * 穿透对一是遍历entities筛选includes的值
     * @param runtimeContext
     * @param navigateMetadata
     * @param includes
     * @param mappingRow
     * @return
     */
    RelationIncludeGetter getDirectToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, List<RelationExtraEntity> includes, List<Object> mappingRow);

    /**
     * 多对一是遍历entities筛选includes的值
     * @param runtimeContext
     * @param navigateMetadata
     * @param targetPropertyNames
     * @param includes
     * @return
     */
    RelationIncludeGetter getManyToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes);

    /**
     * 一对多是遍历entities筛选includes的值
     * @param runtimeContext
     * @param navigateMetadata
     * @param targetPropertyNames
     * @param includes
     * @return
     */
    RelationIncludeGetter getOneToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes);

    /**
     * 多对多是遍历entities筛选includes的值通过mappingRows
     * @param runtimeContext
     * @param navigateMetadata
     * @param targetPropertyNames
     * @param includes
     * @param mappingRows
     * @return
     */
    RelationIncludeGetter getManyToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes, List<Object> mappingRows);
}
