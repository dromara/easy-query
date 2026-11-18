package com.easy.query.core.migration;

import java.util.List;

/**
 * create time 2025/1/11 13:51
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MigrationCommand {
    List<String> getSQLList();
}
