package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8AutoBProxy;
import lombok.Data;

/**
 * create time 2025/11/29 11:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("m8_auto_b")
@EntityProxy
@Data
public class M8AutoB implements ProxyEntityAvailable<M8AutoB , M8AutoBProxy> {
    @Column(primaryKey = true,generatedKey = true)
    private Long id;
    private String aid;
    private String column1;
    private String column2;
    private String column3;
}
