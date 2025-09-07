package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootOneProxy;
import lombok.Data;

/**
 * create time 2025/9/7 16:58
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_root_one")
public class M8SaveRootOne implements ProxyEntityAvailable<M8SaveRootOne , M8SaveRootOneProxy> {
    private String id;
    private String rootId;
    private String name;
    private String tenantId;
}
