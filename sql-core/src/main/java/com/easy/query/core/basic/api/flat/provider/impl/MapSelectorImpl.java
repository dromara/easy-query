package com.easy.query.core.basic.api.flat.provider.impl;

import com.easy.query.core.basic.api.flat.provider.AbstractMapTable;
import com.easy.query.core.basic.api.flat.provider.MapSelector;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.builder.impl.AsSelectorImpl;
import com.easy.query.core.expression.builder.impl.SelectorImpl;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.impl.ColumnAsSelectorImpl;
import com.easy.query.core.expression.parser.core.base.impl.ColumnSelectorImpl;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Map;

/**
 * create time 2024/3/26 16:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapSelectorImpl extends AbstractMapTable implements MapSelector {

    public MapSelectorImpl(ClientQueryable<?> queryable){
        super(queryable);
    }
    @Override
    public ColumnSelector<?> getSelector(int tableIndex) {
        return new ColumnSelectorImpl<>(getTable(tableIndex), new SelectorImpl(entityQueryExpressionBuilder, entityQueryExpressionBuilder.getProjects()));
    }

    @Override
    public ColumnAsSelector<?,?> getAsSelector(int tableIndex) {
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(Map.class);
        return new ColumnAsSelectorImpl<>(getTable(tableIndex), new AsSelectorImpl(entityQueryExpressionBuilder, entityQueryExpressionBuilder.getProjects(), entityMetadata));
    }
}
