package com.easy.query.core.sharding.router.table;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/5 13:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRouteUnit extends TableUnit {

     TableAvailable getTable();

}
