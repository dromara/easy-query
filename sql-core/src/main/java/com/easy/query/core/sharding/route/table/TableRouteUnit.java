package com.easy.query.core.sharding.route.table;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Objects;

/**
 * create time 2023/4/5 13:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRouteUnit extends TableUnit {

     TableAvailable getTable();

}
