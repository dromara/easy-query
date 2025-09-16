package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveCProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveDProxy;
import lombok.Data;

/**
 * create time 2025/9/15 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_d")
public class M8SaveD implements ProxyEntityAvailable<M8SaveD, M8SaveDProxy> {
    @Column(primaryKey = true)
    private String id;
    private String pid;
    private String name;
}
