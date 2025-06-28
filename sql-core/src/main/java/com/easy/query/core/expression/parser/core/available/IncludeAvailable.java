package com.easy.query.core.expression.parser.core.available;

import com.easy.query.core.basic.api.select.ClientQueryable;

import java.util.List;
import java.util.function.Function;

/**
 * create time 2025/6/27 11:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeAvailable {
    String getNavValue();

    List<Function<ClientQueryable<?>,ClientQueryable<?>>> getFunctions();

    List<IncludeAvailable> getIncludes();
}
