package com.easy.query.core.proxy;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/7/11 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TablePropColumn extends PropColumn {
    TableAvailable getTable();
}
