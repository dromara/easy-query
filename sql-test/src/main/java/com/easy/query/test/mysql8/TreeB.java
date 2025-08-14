package com.easy.query.test.mysql8;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.proxy.TreeAProxy;
import com.easy.query.test.mysql8.proxy.TreeBProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/8/14 14:04
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_treeb")
@EntityProxy
@FieldNameConstants
public class TreeB implements ProxyEntityAvailable<TreeB, TreeBProxy> {

    @Column(primaryKey = true)
    private String id;
    private String aid;
    private String apid;
}
