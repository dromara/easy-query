package com.easy.query.test.keytest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.keytest.proxy.MyTestPrimaryKeyProxy;
import lombok.Data;

/**
 * create time 2024/6/20 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_test_p")
@EntityProxy
public class MyTestPrimaryKey implements ProxyEntityAvailable<MyTestPrimaryKey , MyTestPrimaryKeyProxy> {
    @Column(primaryKey = true,primaryKeyGenerator = MyTestPrimaryKeyGenerator.class)
    private String id;

}
