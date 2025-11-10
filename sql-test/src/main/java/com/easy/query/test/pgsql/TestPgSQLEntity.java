package com.easy.query.test.pgsql;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.pgsql.proxy.TestPgSQLEntityProxy;
import lombok.Data;
import org.postgresql.util.PGobject;

import java.sql.JDBCType;

/**
 * create time 2025/5/16 13:39
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("aaaaa")
@EntityProxy
public class TestPgSQLEntity implements ProxyEntityAvailable<TestPgSQLEntity , TestPgSQLEntityProxy> {
    private String id;
//    @Column(jdbcType = JDBCType.BINARY)
//    private String json;
}
