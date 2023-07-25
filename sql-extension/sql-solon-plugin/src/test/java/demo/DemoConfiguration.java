package demo;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

/**
 * create time 2023/7/25 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
public class DemoConfiguration {
    @Bean(value = "db1")
    public DataSource db1DataSource(@Inject("${db1}") HikariDataSource dataSource){
        return dataSource;
    }
}
