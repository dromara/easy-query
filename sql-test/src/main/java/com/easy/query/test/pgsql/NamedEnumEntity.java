package com.easy.query.test.pgsql;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.enums.NamedEnum;
import com.easy.query.test.pgsql.proxy.NamedEnumEntityProxy;
import lombok.Data;

/**
 * create time 2026/4/20 11:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("t_named_enum")
public class NamedEnumEntity implements ProxyEntityAvailable<NamedEnumEntity , NamedEnumEntityProxy> {
    private String id;
    private String name;
    private NamedEnum type;
}
