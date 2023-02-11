package org.jdqc.core.impl;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.jdqc.core.abstraction.JDQCRuntimeContext;
import org.jdqc.core.config.JDQCConfiguration;
import org.jdqc.core.exception.JDQCException;
import org.jdqc.core.query.builder.SelectTableInfo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @FileName: SelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:39
 * @Created by xuejiaming
 */
public abstract class SelectContext {
    private final JDQCRuntimeContext runtimeContext;

    public JDQCRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
    private final String alias;

    private int skip;
    private int take;

    private final List<SelectTableInfo> tables;
    private final List<Object> params;

    private  StringBuilder where;
//    private  StringBuilder select;
    private  StringBuilder group;
    private  StringBuilder order;

    String dbName = "dbdbd0";
    String ip = "127.0.0.1";
    String port = "3306";
    String username = "root";
    String password = "root";
    String driverClassName = "com.mysql.cj.jdbc.Driver";
    private final  DataSource dataSource;


    public SelectContext(JDQCRuntimeContext runtimeContext){
        this(runtimeContext,"t");
    }
    public SelectContext(JDQCRuntimeContext runtimeContext, String alias){
        this.runtimeContext = runtimeContext;
        this.alias = alias;
        this.tables =new ArrayList<>();
        this.params =new ArrayList<>();

        // 设置properties
        Properties properties = new Properties();
        properties.setProperty("name", dbName);
        properties.setProperty(
                "url",
                "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true");
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        int i = Runtime.getRuntime().availableProcessors();
        properties.setProperty("initialSize", String.valueOf(i));
        properties.setProperty("maxActive", String.valueOf(2 * i + 1));
        properties.setProperty("minIdle", String.valueOf(i));
        try {
            this.dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public abstract SelectContext copy();

    public List<SelectTableInfo> getTables() {
        return tables;
    }
    public SelectTableInfo getTable(int index) {
        return tables.get(index);
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public StringBuilder getWhere() {
        if(where==null){
            where=new StringBuilder();
        }
        return where;
    }
    public void addSelectTable(SelectTableInfo selectTableInfo){
        this.tables.add(selectTableInfo);
    }

    /**
     * 数据库别名 默认t
     * @return
     */

    public String getAlias() {
        return alias;
    }

    /**
     * 获取下次表索引
     * @return
     */
    public int getNextTableIndex(){
        return this.tables.size();
    }
    public SelectTableInfo getCurrentPredicateTable(){
        return this.getPredicateTableByOffset(0);
    }
    public SelectTableInfo getPreviewPredicateTable(){
        return this.getPredicateTableByOffset(1);
    }
    public SelectTableInfo getPredicateTableByOffset(int offsetForward){
        if(this.tables.isEmpty()){
            throw new JDQCException("cant get current join table");
        }
        int i = getNextTableIndex() -1 - offsetForward;
        return this.tables.get(i);
    }

//    public StringBuilder getSelect() {
//        if(select==null){
//            select=new StringBuilder();
//        }
//        return select;
//    }

    public JDQCConfiguration getJdqcConfiguration() {
        return runtimeContext.getJDQCConfiguration();
    }

    public StringBuilder getGroup() {
        if(group==null){
            group=new StringBuilder();
        }
        return group;
    }

    public StringBuilder getOrder() {
        if(order==null){
            order=new StringBuilder();
        }
        return order;
    }

    public List<Object> getParams() {
        return params;
    }



    public Connection getConn(){

        Connection conn =null;
        try {
            conn=dataSource.getConnection();
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
