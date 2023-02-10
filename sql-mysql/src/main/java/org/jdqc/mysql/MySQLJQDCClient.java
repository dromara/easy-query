package org.jdqc.mysql;

import org.jdqc.mysql.base.MySQLSelect1;
import org.jdqc.mysql.base.MySQLSelectContext;
import org.jdqc.core.abstraction.client.JQDCClient;
import org.jdqc.core.abstraction.sql.Select1;
import org.jdqc.core.config.JDQCConfiguration;

/**
 *
 * @FileName: DefaultJQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:28
 * @Created by xuejiaming
 */
public class MySQLJQDCClient implements JQDCClient {
    private final JDQCConfiguration configuration;
    public MySQLJQDCClient(JDQCConfiguration configuration){

        this.configuration = configuration;
    }
    @Override
    public <T1> Select1<T1> select(Class<T1> clazz) {
        return new MySQLSelect1<>(clazz,new MySQLSelectContext(configuration));
    }

    @Override
    public <T1> Select1<T1> select(Class<T1> clazz, String alias) {
        return new MySQLSelect1<>(clazz,new MySQLSelectContext(configuration,alias));
    }

}
