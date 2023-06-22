package com.easy.query.api.proxy.core;

import com.easy.query.api.proxy.core.base.SQLGroup;
import com.easy.query.api.proxy.core.base.SQLOrder;
import com.easy.query.api.proxy.core.base.SQLSelect;
import com.easy.query.api.proxy.core.base.SQLSelectAs;
import com.easy.query.api.proxy.core.base.SQLSelectResult;
import com.easy.query.api.proxy.core.base.SQLWhere;
import com.easy.query.api.proxy.core.base.SQLWhereAggregate;
import com.easy.query.api.proxy.core.base.TableProxy;

/**
 * create time 2023/6/21 23:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLQuery<T extends SQLQuery<T, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TClass>,
        TWhere extends SQLWhere,
        TWhereAggregate extends SQLWhereAggregate,
        TSelect extends SQLSelect,
        TSelectAs extends SQLSelectAs,
        TSelectResult extends SQLSelectResult,
        TOrder extends SQLOrder,
        TGroup extends SQLGroup,
        TClass>
        extends TableProxy<T,TClass> {
    TWhere createWhere();
    TWhereAggregate createWhereAggregate();
    TSelect createSelect();
    TSelectAs createSelectAs();
    TSelectResult createSelectResult();
    TOrder createOrder();
    TGroup createGroup();
}
