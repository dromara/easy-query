package com.easy.query.sql.starter.con;

import com.easy.query.core.datasource.DefaultDataSourceUnit;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/5/27 23:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class SpringDataSourceUnit extends DefaultDataSourceUnit {
    public SpringDataSourceUnit(String dataSourceName, DataSource dataSource, int dataSourcePool) {
        super(dataSourceName,dataSource,dataSourcePool);
    }

    @Override
    protected List<Connection> getConnections(int count) throws SQLException {
        ArrayList<Connection> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            result.add(connection);
        }
        return result;
    }
}
