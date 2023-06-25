package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyUpdateSetSelector;
import com.easy.query.core.expression.builder.UpdateSetSelector;

/**
 * create time 2023/6/25 19:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyUpdateSetSelectorImpl implements ProxyUpdateSetSelector {
    private final UpdateSetSelector updateSetSelector;

    public ProxyUpdateSetSelectorImpl(UpdateSetSelector updateSetSelector){

        this.updateSetSelector = updateSetSelector;
    }
    @Override
    public UpdateSetSelector getUpdateSetSelector() {
        return updateSetSelector;
    }
}
