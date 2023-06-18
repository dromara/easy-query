package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.function.Function;

/**
 * create time 2023/6/18 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateIncludeImpl<TEntity> implements NavigateInclude<TEntity> {
    private final int index;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final TableAvailable entityTable;

    public NavigateIncludeImpl(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder){

        this.index = index;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.entityTable = entityQueryExpressionBuilder.getTable(index).getEntityTable();
    }
    @Override
    public <TProperty> NavigateInclude<TEntity> with(String property, Function<ClientQueryable<TProperty>, ClientQueryable<TProperty>> func) {
        NavigateMetadata navigateMetadata = entityTable.getEntityMetadata().getNavigateNotNull(property);
        Class<?> navigatePropertyType = navigateMetadata.getNavigatePropertyType();
        return null;
    }
}
