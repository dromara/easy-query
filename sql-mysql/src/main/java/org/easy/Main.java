package org.easy;

import org.easy.query.core.abstraction.DefaultEasyQueryRuntimeContext;
import org.easy.query.core.abstraction.client.JQDCClient;
import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
import org.easy.query.core.config.NameConversion;
import org.easy.query.core.config.UnderlinedNameConversion;
import org.easy.query.core.metadata.DefaultEntityMetadataManager;
import org.easy.query.core.config.EasyQueryConfiguration;
import org.easy.query.mysql.MySQLJQDCClient;
import org.easy.query.mysql.config.MySQLDialect;
import org.easy.test.SysUserLogbyMonth;
import org.easy.test.TestUserMysql;

public class Main {
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private static final String[] scanPackages=new String[]{"org.jdqc.core"};
    private static JQDCClient client;
    public static void main(String[] args) {
        NameConversion nameConversion = new UnderlinedNameConversion();
        EasyQueryConfiguration configuration = new EasyQueryConfiguration(driver);
        configuration.setNameConversion(nameConversion);
        configuration.setDialect(new MySQLDialect());
        EntityMetadataManager entityMetadataManager = new DefaultEntityMetadataManager(configuration);
        DefaultEasyQueryRuntimeContext jqdcRuntimeContext = new DefaultEasyQueryRuntimeContext(configuration, entityMetadataManager);
//        String[] packages = scanPackages;
//        for (String packageName : packages) {
//            List<EntityMetadata> entityMetadataList = JDQCUtil.loadPackage(packageName, configuration);
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
//        configuration.addTableInfo(tableInfo);
//        TableInfo tableInfo1 = new TableInfo(TestUser1.class,"TestUser1");
//        tableInfo1.getColumns().putIfAbsent("id",new ColumnInfo(tableInfo1,"id"));
//        tableInfo1.getColumns().putIfAbsent("name",new ColumnInfo(tableInfo1,"name"));
//        tableInfo1.getColumns().putIfAbsent("uid",new ColumnInfo(tableInfo1,"uid"));
//        configuration.addTableInfo(tableInfo1);
        client=new MySQLJQDCClient(jqdcRuntimeContext);

        TestUserMysql testUserMysql = client.select(TestUserMysql.class,"y")
                .where(o -> o.eq(TestUserMysql::getId, "102")).firstOrNull();

        SysUserLogbyMonth sysUserLogbyMonth = client.select(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();


        System.out.println("Hello world!");
    }
}