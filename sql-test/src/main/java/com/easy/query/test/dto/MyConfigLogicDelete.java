package com.easy.query.test.dto;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.dto.proxy.MyConfigLogicDeleteProxy;
import lombok.Data;

/**
 * create time 2026/1/30 20:34
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("my_config_logic")
@Data
@EntityProxy
public class MyConfigLogicDelete implements ProxyEntityAvailable<MyConfigLogicDelete , MyConfigLogicDeleteProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private Boolean deleted;
}
