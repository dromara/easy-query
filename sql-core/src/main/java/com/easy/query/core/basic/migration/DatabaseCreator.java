package com.easy.query.core.basic.migration;

import com.easy.query.core.annotation.Nullable;

/**
 * create time 2023/11/27 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DatabaseCreator {
    /**
     * 确认并且创建数据库
     * @return true:创建成功,false:已存在
     */
    boolean ensureCreated();
    String generateScript(@Nullable String fromMigration, @Nullable String toMigration);
}
