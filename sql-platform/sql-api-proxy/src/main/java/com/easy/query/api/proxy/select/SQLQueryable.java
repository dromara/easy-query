package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.core.SQLQuery;
import com.easy.query.api.proxy.core.base.SQLGroup;
import com.easy.query.api.proxy.core.base.SQLOrder;
import com.easy.query.api.proxy.core.base.SQLSelect;
import com.easy.query.api.proxy.core.base.SQLSelectAs;
import com.easy.query.api.proxy.core.base.SQLSelectResult;
import com.easy.query.api.proxy.core.base.SQLWhere;
import com.easy.query.api.proxy.core.base.SQLWhereAggregate;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/6/21 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLQueryable<TProxy extends SQLQuery<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity>,
        TWhere extends SQLWhere,
        TWhereAggregate extends SQLWhereAggregate,
        TSelect extends SQLSelect,
        TSelectAs extends SQLSelectAs,
        TSelectResult extends SQLSelectResult,
        TOrder extends SQLOrder,
        TGroup extends SQLGroup,
        TEntity> extends Query<TEntity>,
        Interceptable<SQLQueryable<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity>>,
        LogicDeletable<SQLQueryable<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity>>,
        TableReNameable<SQLQueryable<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity>>,
        QueryStrategy<SQLQueryable<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity>> {
    SQLQueryable<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity> where(SQLExpression1<TWhere> whereExpression);

    SQLQueryable<TProxy, TWhere,TWhereAggregate, TSelect,TSelectAs,TSelectResult, TOrder, TGroup, TEntity> select(SQLExpression1<TSelect> selectExpression);

    <TProxy1 extends SQLQuery<TProxy1, TWhere1,TWhereAggregate1, TSelect1,TSelectAs1,TSelectResult1, TOrder1, TGroup1, TEntity1>,
            TWhere1 extends SQLWhere,
            TWhereAggregate1 extends SQLWhereAggregate,
            TSelect1 extends SQLSelect,
            TSelectAs1 extends SQLSelectAs,
            TSelectResult1 extends SQLSelectResult,
            TOrder1 extends SQLOrder,
            TGroup1 extends SQLGroup,
            TEntity1> SQLQueryable<TProxy1, TWhere1,TWhereAggregate1, TSelect1,TSelectAs1,TSelectResult1, TOrder1, TGroup1, TEntity1> select(TProxy1 selector, SQLExpression2<TSelect,TSelect1> selectExpression);
}
