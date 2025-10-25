package com.easy.query.core.proxy.sql.include;

import com.easy.query.core.expression.parser.core.available.IncludeAvailable;

import java.util.List;

/**
 * create time 2025/10/25 15:51
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeCollectorResult {
    List<IncludeAvailable> getIncludes();
}
