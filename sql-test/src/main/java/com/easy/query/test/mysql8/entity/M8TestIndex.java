package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.TableIndex;
import com.easy.query.core.annotation.TableIndexes;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8TestIndexProxy;
import lombok.Data;

/**
 * create time 2025/4/27 14:45
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table
@EntityProxy
@TableIndexes({
        @TableIndex(fields = {"column1","column2"}),
        @TableIndex(fields = {"column2"},unique = true)
})
public class M8TestIndex implements ProxyEntityAvailable<M8TestIndex , M8TestIndexProxy> {
    @Column(primaryKey = true)
    private String column1;
    private String column2;
    private String column3;
    private String column4;
}
