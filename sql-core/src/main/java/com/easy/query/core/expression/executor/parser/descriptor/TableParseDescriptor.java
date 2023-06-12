package com.easy.query.core.expression.executor.parser.descriptor;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Set;

/**
 * create time 2023/5/18 17:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableParseDescriptor {
    /**
     * 分片的对象
     * @return
     */
    Set<TableAvailable> getTables();
}
