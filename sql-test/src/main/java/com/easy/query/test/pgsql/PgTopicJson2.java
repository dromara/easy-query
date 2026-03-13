package com.easy.query.test.pgsql;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.pgsql.proxy.PgTopicJson2Proxy;
import com.easy.query.test.pgsql.proxy.PgTopicJsonProxy;
import lombok.Data;

import java.sql.JDBCType;
import java.util.List;

/**
 * create time 2025/3/4 08:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_test_json2")
@Data
@EntityProxy
public class PgTopicJson2 implements ProxyEntityAvailable<PgTopicJson2, PgTopicJson2Proxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    //如果不使用code-first那么可以不添加该注解和数据库类型
    @Column(dbType = "jsonb")
    private TopicExtraJson extraJson;
    //如果不使用code-first那么可以不添加该注解和数据库类型
    @Column(dbType = "jsonb")
    private List<TopicExtraJson> extraJsonArray;
}
