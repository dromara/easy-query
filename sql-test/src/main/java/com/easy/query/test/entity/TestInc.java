package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TestIncProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * create time 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_test_inc")
@EntityProxy
public class TestInc implements ProxyEntityAvailable<TestInc , TestIncProxy> {

    @Column(primaryKey = true,generatedKey = true)
    private Integer id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}
