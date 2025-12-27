package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.OffsetChunkTestProxy;
import lombok.Data;

/**
 * create time 2025/12/24 16:48
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("offset_chunk_test")
@EntityProxy
public class OffsetChunkTest implements ProxyEntityAvailable<OffsetChunkTest , OffsetChunkTestProxy> {
    @Column(primaryKey = true)
    private String id;

    private Integer seq;

    private String status;
}
