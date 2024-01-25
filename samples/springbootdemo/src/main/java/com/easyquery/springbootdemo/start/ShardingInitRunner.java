//package com.easyquery.springbootdemo.start;
//
//import com.alibaba.druid.pool.DruidDataSourceFactory;
//import com.easy.query.api4j.client.EasyQuery;
//import com.easy.query.core.datasource.DataSourceManager;
//import com.easy.query.core.exception.EasyQueryException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * create time 2023/6/4 21:40
// * 文件说明
// *
// * @author xuejiaming
// */
//@Component
//public class ShardingInitRunner implements ApplicationRunner {
//    @Autowired
//    private EasyQuery easyQuery;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        Map<String, DataSource> dataSources = createDataSources();
//        DataSourceManager dataSourceManager = easyQuery.getRuntimeContext().getDataSourceManager();
//        for (Map.Entry<String, DataSource> stringDataSourceEntry : dataSources.entrySet()) {
//
//            dataSourceManager.addDataSource(stringDataSourceEntry.getKey(), stringDataSourceEntry.getValue(), 60);
//        }
//        System.out.println("初始化完成");
//    }
//
//    private Map<String, DataSource> createDataSources() {
//        HashMap<String, DataSource> stringDataSourceHashMap = new HashMap<>();
//        for (int i = 1; i < 4; i++) {
//            DataSource dataSource = createDataSource("ds" + i, "jdbc:mysql://127.0.0.1:3306/easy-sharding-test" + i + "?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true", "root", "root");
//            stringDataSourceHashMap.put("ds" + i, dataSource);
//        }
//        return stringDataSourceHashMap;
//    }
//
//    private DataSource createDataSource(String dsName, String url, String username, String password) {
//
//        // 设置properties
//        Properties properties = new Properties();
//        properties.setProperty("name", dsName);
//        properties.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
//        properties.setProperty("url", url);
//        properties.setProperty("username", username);
//        properties.setProperty("password", password);
//        properties.setProperty("initialSize", "10");
//        properties.setProperty("maxActive", "100");
//        try {
//            return DruidDataSourceFactory.createDataSource(properties);
//        } catch (Exception e) {
//            throw new EasyQueryException(e);
//        }
//    }
//}
