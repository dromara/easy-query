package com.easy.query.core.basic.api.flat.provider.impl;

import com.easy.query.core.basic.api.flat.provider.AbstractMapTable;
import com.easy.query.core.basic.api.flat.provider.MapGroupSelector;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.builder.impl.GroupSelectorImpl;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.impl.ColumnGroupSelectorImpl;

/**
 * create time 2024/3/26 16:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapGroupSelectorImpl extends AbstractMapTable implements MapGroupSelector {

    public MapGroupSelectorImpl(ClientQueryable<?> queryable){
      super(queryable);
    }
    @Override
    public ColumnGroupSelector<?> getGroupSelector(int tableIndex) {
        return new ColumnGroupSelectorImpl<>(getTable(tableIndex), new GroupSelectorImpl(entityQueryExpressionBuilder));
    }
}
