package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.core.expression.builder.GroupSelector;

/**
 * create time 2023/6/23 23:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyGroupSelectorImpl implements ProxyGroupSelector {
    private final GroupSelector groupSelector;

    public ProxyGroupSelectorImpl(GroupSelector groupSelector){

        this.groupSelector = groupSelector;
    }
    @Override
    public GroupSelector getGroupSelector() {
        return groupSelector;
    }

}
