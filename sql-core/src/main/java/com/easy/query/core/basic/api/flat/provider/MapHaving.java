package com.easy.query.core.basic.api.flat.provider;

import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2024/3/26 17:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapHaving extends MapTable{

    WhereAggregatePredicate<?> getHavingPredicate(int tableIndex);
}

