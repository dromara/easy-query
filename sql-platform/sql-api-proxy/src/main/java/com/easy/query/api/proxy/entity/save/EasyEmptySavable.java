package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collections;
import java.util.List;

/**
 * create time 2025/9/5 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptySavable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntitySavable<TProxy,T>{
    @Override
    public List<T> getEntities() {
        return Collections.emptyList();
    }

    @Override
    public void executeCommand() {

    }
}
