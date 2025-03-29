package com.easy.query.core.expression.implicit;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.metadata.NavigateMetadata;

/**
 * create time 2025/3/19 16:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityRelationPredicateProvider {
    String getName();

    void selfTargetPropertyPredicate(TableAvailable selfTable, String[] selfProps, WherePredicate<?> targetWherePredicate,String[] targetProps);
    void targetTargetMappingPropertyPredicate(TableAvailable targetTable, String[] targetProps, WherePredicate<?> mappingWherePredicate,String[] targetMappingProps);
    void selfSelfMappingPropertyPredicate(TableAvailable selfTable, String[] selfProps, WherePredicate<?> mappingWherePredicate,String[] selfMappingProps);

    /**
     * 隐式子查询
     * @param leftTable
     * @param navigateMetadata
     * @param runtimeContext
     * @return
     * @param <T>
     */
    <T> ClientQueryable<T> toImplicitSubQuery(TableAvailable leftTable, NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext);

    /**
     * 隐式join
     * @param entityExpressionBuilder
     * @param leftTable
     * @param property
     * @param fullName
     * @return
     */
    TableAvailable toImplicitJoin(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, String fullName);
}
