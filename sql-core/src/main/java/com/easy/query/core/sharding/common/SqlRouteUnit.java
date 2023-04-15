package com.easy.query.core.sharding.common;

import com.easy.query.core.sharding.merge.executor.common.SqlUnit;
import com.easy.query.core.sharding.route.table.engine.TableRouteResult;

/**
 * create time 2023/4/5 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SqlRouteUnit {
    String getDataSourceName();
    TableRouteResult getTableRouteResult();

    SqlUnit getSqlUnit();
}
