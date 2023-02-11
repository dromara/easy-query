package org.jdqc;

import org.jdqc.core.TestUser;
import org.jdqc.core.abstraction.DefaultJDQCRuntimeContext;
import org.jdqc.core.abstraction.client.JQDCClient;
import org.jdqc.core.abstraction.metadata.EntityMetadataManager;
import org.jdqc.core.config.NameConversion;
import org.jdqc.core.config.UnderlinedNameConversion;
import org.jdqc.core.metadata.DefaultEntityMetadataManager;
import org.jdqc.core.config.JDQCConfiguration;
import org.jdqc.core.util.JDQCUtil;
import org.jdqc.mysql.MySQLJQDCClient;
import org.jdqc.test.SysUserLogbyMonth;
import org.jdqc.test.TestUserMysql;

import java.util.List;

public class Main {
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private static final String[] scanPackages=new String[]{"org.jdqc.core"};
    private static JQDCClient client;
    public static void main(String[] args) {
        NameConversion nameConversion = new UnderlinedNameConversion();
        JDQCConfiguration jdqcConfiguration = new JDQCConfiguration(driver,nameConversion);
        EntityMetadataManager entityMetadataManager = new DefaultEntityMetadataManager(jdqcConfiguration);
        DefaultJDQCRuntimeContext jqdcRuntimeContext = new DefaultJDQCRuntimeContext(jdqcConfiguration, entityMetadataManager);
//        String[] packages = scanPackages;
//        for (String packageName : packages) {
//            List<EntityMetadata> entityMetadataList = JDQCUtil.loadPackage(packageName, jdqcConfiguration);
//            if(!entityMetadataList.isEmpty()){
//                for (EntityMetadata entityMetadata : entityMetadataList) {
//                    entityMetadataManager.addEntityMetadata(entityMetadata);
//                }
//            }
//        }


//        TableInfo tableInfo = new TableInfo(TestUser.class,"TestUser");
//        tableInfo.getColumns().putIfAbsent("id",new ColumnInfo(tableInfo,"id"));
//        tableInfo.getColumns().putIfAbsent("name",new ColumnInfo(tableInfo,"name"));
//        tableInfo.getColumns().putIfAbsent("studentName",new ColumnInfo(tableInfo,"age"));
//        jdqcConfiguration.addTableInfo(tableInfo);
//        TableInfo tableInfo1 = new TableInfo(TestUser1.class,"TestUser1");
//        tableInfo1.getColumns().putIfAbsent("id",new ColumnInfo(tableInfo1,"id"));
//        tableInfo1.getColumns().putIfAbsent("name",new ColumnInfo(tableInfo1,"name"));
//        tableInfo1.getColumns().putIfAbsent("uid",new ColumnInfo(tableInfo1,"uid"));
//        jdqcConfiguration.addTableInfo(tableInfo1);
        client=new MySQLJQDCClient(jqdcRuntimeContext);

        TestUserMysql testUserMysql = client.select(TestUserMysql.class)
                .where(o -> o.eq(TestUserMysql::getId, "102")).firstOrNull();

        SysUserLogbyMonth sysUserLogbyMonth = client.select(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();


        System.out.println("Hello world!");
    }
}