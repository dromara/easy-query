package com.easy.query.test.testvo;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.testvo.proxy.MyTopicTestDTOProxy;
import lombok.Data;

/**
 * create time 2024/4/16 15:56
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_topic")
@EntityProxy
public class MyTopicTestDTO implements ProxyEntityAvailable<MyTopicTestDTO , MyTopicTestDTOProxy> {
    @Column(primaryKey = true,value = "id")
    private String eCgi;
    @Column(value = "title")
    private String SQL;
    @Column(value = "stars")
    private Integer Stars;
}
