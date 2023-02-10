package org.jdqc;

import org.jdqc.core.TestUser;
import org.jdqc.core.TestUser1;
import org.jdqc.core.abstraction.client.JQDCClient;
import org.jdqc.core.config.JDQCConfiguration;
import org.jdqc.core.metadata.ColumnInfo;
import org.jdqc.core.metadata.TableInfo;
import org.jdqc.mysql.MySQLJQDCClient;

import java.util.List;

public class Main {
    private static JQDCClient client;
    public static void main(String[] args) {
        JDQCConfiguration jdqcConfiguration = new JDQCConfiguration("com.mysql.cj.jdbc.Driver");
        TableInfo tableInfo = new TableInfo(TestUser.class,"TestUser");
        tableInfo.getColumns().putIfAbsent("id",new ColumnInfo(tableInfo,"id"));
        tableInfo.getColumns().putIfAbsent("name",new ColumnInfo(tableInfo,"name"));
        tableInfo.getColumns().putIfAbsent("studentName",new ColumnInfo(tableInfo,"age"));
        jdqcConfiguration.addTableInfo(tableInfo);
        TableInfo tableInfo1 = new TableInfo(TestUser1.class,"TestUser1");
        tableInfo1.getColumns().putIfAbsent("id",new ColumnInfo(tableInfo1,"id"));
        tableInfo1.getColumns().putIfAbsent("name",new ColumnInfo(tableInfo1,"name"));
        tableInfo1.getColumns().putIfAbsent("uid",new ColumnInfo(tableInfo1,"uid"));
        jdqcConfiguration.addTableInfo(tableInfo1);
        client=new MySQLJQDCClient(jdqcConfiguration);


        TestUser testUser = client.select(TestUser.class)
                .where(o -> o.eq(TestUser::getId, "102")).firstOrNull();
        System.out.println(testUser);
        List<TestUser> testUsers = client.select(TestUser.class).toList();

        System.out.println(testUsers);
        System.out.println("Hello world!");
    }
}