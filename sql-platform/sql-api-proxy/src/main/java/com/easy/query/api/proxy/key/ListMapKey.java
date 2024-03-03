package com.easy.query.api.proxy.key;

import com.easy.query.core.util.EasyObjectUtil;

import java.util.List;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class ListMapKey<TEntity> implements MapKey<List<TEntity>> {
    private final String name;

    public ListMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<List<TEntity>> getPropType() {
        return EasyObjectUtil.typeCastNullable(List.class);
    }
}
