package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.base.proxy.AreaProxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/17 21:27
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_area")
@Data
@ToString
@EntityProxy
public class Area implements ProxyEntityAvailable<Area , AreaProxy> {
    @Column(primaryKey = true)
    private String code;
    private String provinceCode;
    private String cityCode;
    private String name;
}
