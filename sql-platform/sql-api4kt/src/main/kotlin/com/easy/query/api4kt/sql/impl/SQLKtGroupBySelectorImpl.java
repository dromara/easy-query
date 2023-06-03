package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 */
public class SQLKtGroupBySelectorImpl<T1> implements SQLKtGroupBySelector<T1> {

    private final GroupBySelector<T1> groupBySelector;

    public SQLKtGroupBySelectorImpl(GroupBySelector<T1> groupBySelector) {
        this.groupBySelector = groupBySelector;
    }


    @Override
    public GroupBySelector<T1> getGroupBySelector() {
        return groupBySelector;
    }
}
