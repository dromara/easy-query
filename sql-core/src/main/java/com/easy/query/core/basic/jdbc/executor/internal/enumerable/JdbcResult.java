package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import java.util.List;

/**
 * create time 2023/7/31 21:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcResult<TR> {
    JdbcStreamResult<TR> getJdbcStreamResult();
    List<TR> toList();
}
