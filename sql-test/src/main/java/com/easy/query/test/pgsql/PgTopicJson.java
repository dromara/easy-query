package com.easy.query.test.pgsql;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.TopicJsonProxy;
import com.easy.query.test.pgsql.proxy.PgTopicJsonProxy;
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
public class PgTopicJson implements ProxyEntityAvailable<PgTopicJson, PgTopicJsonProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Column(dbType = "jsonb",typeHandler = PgSQLJsonbTypeHandler.class)
    private String extraJson;
    @Column(dbType = "jsonb",typeHandler = PgSQLJsonbTypeHandler.class)
    private String extraJsonArray;
}
