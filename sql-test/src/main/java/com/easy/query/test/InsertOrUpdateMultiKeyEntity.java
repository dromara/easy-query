package com.easy.query.test;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.proxy.InsertOrUpdateMultiKeyEntityProxy;
import lombok.Data;

/**
 * create time 2024/7/9 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_user")
@EntityProxy
public class InsertOrUpdateMultiKeyEntity implements ProxyEntityAvailable<InsertOrUpdateMultiKeyEntity , InsertOrUpdateMultiKeyEntityProxy> {
    @Column(primaryKey = true)
    private String column1;
    @Column(primaryKey = true)
    private String column2;
    private String column3;
    private String column4;
    private String column5;
}
