package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TestInsertProxy;
import lombok.Data;

/**
 * create time 2025/2/11 13:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_test_insert")
@EntityProxy
public class TestInsert implements ProxyEntityAvailable<TestInsert , TestInsertProxy> {
    @Column(primaryKey = true)
    private String id;
    private String column1;
    private String column2;
}
