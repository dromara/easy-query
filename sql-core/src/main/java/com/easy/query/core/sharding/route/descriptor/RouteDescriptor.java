package com.easy.query.core.sharding.route.descriptor;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/5/18 16:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RouteDescriptor {
    /**
     * 针对哪个表名
     * @return
     */
    TableAvailable getTable();
}
