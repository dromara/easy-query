package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.TopicJsonProxy;
import lombok.Data;

/**
 * create time 2025/3/4 08:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_test_json")
@Data
@EntityProxy
public class TopicJson implements ProxyEntityAvailable<TopicJson , TopicJsonProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Column(dbType = "varchar(255)")
    private String extraJson;
}
