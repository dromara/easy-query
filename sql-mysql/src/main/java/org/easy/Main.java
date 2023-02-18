package org.easy;

import org.easy.query.core.abstraction.*;
import org.easy.query.core.abstraction.client.JQDCClient;
import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
import org.easy.query.core.config.*;
import org.easy.query.core.metadata.DefaultEntityMetadataManager;
import org.easy.query.mysql.MySQLJQDCClient;
import org.easy.query.mysql.config.MySQLDialect;
import org.easy.test.DefaultDataSourceFactory;
import org.easy.test.SysUserLogbyMonth;
import org.easy.test.TestUserMysql;
import org.easy.test.TestUserMysqlx;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private static final String username="root";
    private static final String password="root";
    private static final String url="jdbc:mysql://127.0.0.1:3306/dbdbd0?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true";
    private static JQDCClient client;
    public static void main(String[] args) {

        DefaultConfig defaultConfig = new DefaultConfig("easy-query",driver, username, password, url);
        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(defaultConfig);
        EasyConnector easyConnector= new DataSourceConnector(defaultDataSourceFactory);
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        EasyJdbcTypeHandler jdbcTypeHandler = new DefaultJdbcTypeHandler();
        NameConversion nameConversion = new UnderlinedNameConversion();
        EasyQueryConfiguration configuration = new EasyQueryConfiguration();
        configuration.setNameConversion(nameConversion);
        configuration.setDialect(new MySQLDialect());
        EntityMetadataManager entityMetadataManager = new DefaultEntityMetadataManager(configuration);
        EasyQueryLambdaFactory easyQueryLambdaFactory = new DefaultEasyQueryLambdaFactory();
        DefaultEasyQueryRuntimeContext jqdcRuntimeContext = new DefaultEasyQueryRuntimeContext(configuration, entityMetadataManager,easyQueryLambdaFactory,easyConnector,defaultExecutor,jdbcTypeHandler);
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

        SysUserLogbyMonth sysUserLogbyMonth1 = client.select(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            SysUserLogbyMonth sysUserLogbyMonth = client.select(SysUserLogbyMonth.class)
//                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("耗时："+(end-start)+"ms");

        TestUserMysql testUserMysql = client.select(TestUserMysql.class,"y")
                .where(o -> o.eq(TestUserMysql::getId, "102").like(TestUserMysql::getName,"1%").and(x->
                    x.like(TestUserMysql::getName,"123").or().eq(TestUserMysql::getAge,1)
             )).orderByAsc(o->o.column(TestUserMysql::getName).column(TestUserMysql::getAge))
                .orderByDesc(o->o.column(TestUserMysql::getName)).firstOrNull();


        TestUserMysql testUserMysql1 = client.select(TestUserMysql.class,"y")
                .where(o -> o.eq(TestUserMysql::getId, "102").like(TestUserMysql::getName,"1%").and(x->
                    x.like(TestUserMysql::getName,"123").or().eq(TestUserMysql::getAge,1)
             ).or(x->x.eq(TestUserMysql::getName,456).eq(TestUserMysql::getId,8989))).firstOrNull();

        SysUserLogbyMonth sysUserLogbyMonth = client.select(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(sysUserLogbyMonth.getTime());
        System.out.println(sysUserLogbyMonth);
        TestUserMysqlx testUserMysql2 = client.select(TestUserMysql.class)
                .leftJoin(SysUserLogbyMonth.class,(a,b)->a.eq(b,TestUserMysql::getName,SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                .where(o -> o.eq(TestUserMysql::getId, "102")
                        .like(TestUserMysql::getName,"1%")
                        .and(x->x.like(TestUserMysql::getName,"123").or().eq(TestUserMysql::getAge,1)
                )).firstOrNull(TestUserMysqlx.class,x->x.columnAll().columnAs(TestUserMysql::getName,TestUserMysqlx::getName1));

        System.out.println("Hello world!");
    }
}