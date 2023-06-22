package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.core.SQLQuery;
import com.easy.query.api.proxy.core.base.SQLGroup;
import com.easy.query.api.proxy.core.base.SQLOrder;
import com.easy.query.api.proxy.core.base.SQLSelect;
import com.easy.query.api.proxy.core.base.SQLSelectAs;
import com.easy.query.api.proxy.core.base.SQLSelectResult;
import com.easy.query.api.proxy.core.base.SQLWhere;
import com.easy.query.api.proxy.core.base.SQLWhereAggregate;
import com.easy.query.api.proxy.select.SQLQueryable;

/**
 * create time 2023/6/21 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasySQLQuery {
    <TProxy extends SQLQuery<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity>,
            TWhere extends SQLWhere,
            TWhereAggregate extends SQLWhereAggregate,
            TSelect extends SQLSelect,
            TSelectAs extends SQLSelectAs,
            TSelectResult extends SQLSelectResult,
            TOrder extends SQLOrder,
            TGroup extends SQLGroup,
            TEntity> SQLQueryable<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity> queryable(TProxy table);
}
