package com.easy.query.core.basic.jdbc.executor.query;

import com.easy.query.core.sharding.merge.abstraction.StreamResult;

/**
 * create time 2023/4/16 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StreamIterable extends AutoCloseable {
    StreamResult getStreamResult();
}
