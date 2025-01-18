package com.easy.query.core.migration;


import com.easy.query.core.context.QueryRuntimeContext;

import java.util.List;

/**
 * create time 2025/1/11 13:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class MigrationContext {

    private final List<Class<?>> entities;

    public MigrationContext(List<Class<?>> entities) {
        this.entities = entities;
    }

    public List<Class<?>> getEntities() {
        return entities;
    }

}
