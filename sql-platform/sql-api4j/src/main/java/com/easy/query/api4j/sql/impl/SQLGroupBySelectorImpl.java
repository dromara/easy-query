package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 */
public class SQLGroupBySelectorImpl<T1> implements SQLGroupBySelector<T1> {

    private final GroupBySelector<T1> groupBySelector;

    public SQLGroupBySelectorImpl(GroupBySelector<T1> groupBySelector) {
        this.groupBySelector = groupBySelector;
    }


    @Override
    public GroupBySelector<T1> getGroupBySelector() {
        return groupBySelector;
    }
}
