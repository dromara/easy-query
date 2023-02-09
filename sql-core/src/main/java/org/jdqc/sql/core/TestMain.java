package org.jdqc.sql.core;

import org.jdqc.sql.core.abstraction.client.JQDCClient;
import org.jdqc.sql.core.abstraction.sql.Select2;
import org.jdqc.sql.core.config.JDQCConfiguration;
import org.jdqc.sql.core.impl.MySQLJQDCClient;
import org.jdqc.sql.core.schema.ColumnInfo;
import org.jdqc.sql.core.schema.TableInfo;

import java.util.List;

/**
 * @FileName: TestMain.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:42
 * @Created by xuejiaming
 */
public class TestMain {
    private static JQDCClient client;
    public static void main(String[] args) {
        JDQCConfiguration jdqcConfiguration = new JDQCConfiguration();
        TableInfo tableInfo = new TableInfo(TestUser.class);
        tableInfo.getColumns().putIfAbsent("id",new ColumnInfo(tableInfo,"id"));
        tableInfo.getColumns().putIfAbsent("name",new ColumnInfo(tableInfo,"name"));
        tableInfo.getColumns().putIfAbsent("studentName",new ColumnInfo(tableInfo,"student_name"));
        jdqcConfiguration.addTableInfo(tableInfo);
        TableInfo tableInfo1 = new TableInfo(TestUser1.class);
        tableInfo1.getColumns().putIfAbsent("id",new ColumnInfo(tableInfo1,"id"));
        tableInfo1.getColumns().putIfAbsent("name",new ColumnInfo(tableInfo1,"name"));
        tableInfo1.getColumns().putIfAbsent("uid",new ColumnInfo(tableInfo1,"uid"));
        jdqcConfiguration.addTableInfo(tableInfo1);
        client=new MySQLJQDCClient(jdqcConfiguration);
        Select2<TestUser, TestUser1> testUserTestUser1Select2 = client.select(TestUser.class)
                .leftJoin(TestUser1.class, (a, b) -> a.eq(b, TestUser::getId, TestUser1::getUid).and(b).like(TestUser1::getName, "小明").like(TestUser1::getName, "小明"))
                .where(a -> a.eq(TestUser::getId, "1").like(TestUser::getName, "1223"))
                .where((a, b) -> b.eq(TestUser1::getId, "x"))
//                .select((a,b) -> a.column(TestUser::getStudentName).column(TestUser::getId).use(b).column(TestUser1::getUid))
                .groupBy(o -> o.column(TestUser::getStudentName).column(TestUser::getId))
                .groupBy((a, b) -> b.column(TestUser1::getUid));

        String s2 = testUserTestUser1Select2.toSql();
        System.out.println("----------");
        System.out.println(s2);
        List<TestUser> testUsers1 = testUserTestUser1Select2.toList(o->o.column(TestUser::getStudentName));

        System.out.println("----------");
        String s = client.select(TestUser.class)
                .leftJoin(TestUser1.class, (a, b) -> a.eq(b, TestUser::getId, TestUser1::getUid).and(b).like(TestUser1::getName, "小明").like(TestUser1::getName, "小明"))
                .where(a -> a.eq(TestUser::getId, "1").like(TestUser::getName, "1223"))
                .where((a, b) -> b.eq(TestUser1::getId, "x"))
//                .select((a,b) -> a.column(TestUser::getStudentName).column(TestUser::getId).use(b).column(TestUser1::getUid))
                .groupBy(o -> o.column(TestUser::getStudentName).column(TestUser::getId))
                .groupBy((a, b) -> b.column(TestUser1::getUid))
                .toSql();
        System.out.println(s);
        List<TestUser> testUsers = client.select(TestUser.class,"aa")
                .leftJoin(TestUser1.class, (a, b) -> a.eq(b, TestUser::getId, TestUser1::getUid).and(b).like(TestUser1::getName, "小明").like(TestUser1::getName, "小明"))
                .where(a -> a.eq(TestUser::getId, "1").like(TestUser::getName, "1223"))
                .where((a, b) -> b.eq(TestUser1::getId, "x"))
//                .select(a->a.column(TestUser::getStudentName).column(TestUser::getId))
//                .groupBy(o->o.column(TestUser::getStudentName).column(TestUser::getId))
                .groupBy((a,b)->b.column(TestUser1::getUid))
                .toList();
        String s1 = client.select(TestUser.class).where(a -> a.eq(TestUser::getId, "1").like(TestUser::getName, "1223"))
//                .select(o -> o.column(TestUser::getStudentName).column(TestUser::getId).column(TestUser::getName))
                .where(a -> a.eq(TestUser::getStudentName, "1").like(TestUser::getName, "xxx"))
                .toSql(a->a.column(TestUser::getStudentName).column(TestUser::getId).column(TestUser::getName));
        System.out.println(s1);
        List<TestUser> xxx = client.select(TestUser.class).where(a -> a.eq(TestUser::getId, 43).like(TestUser::getName, "1223"))
//                .select(o -> o.column(TestUser::getStudentName).column(TestUser::getId).column(TestUser::getName))
                .where(a -> a.eq(TestUser::getStudentName, "1").like(TestUser::getName, "xxx"))
                .toList();


        TestUser testUser = client.select(TestUser.class)
                .leftJoin(TestUser1.class, (a, b) -> a.eq(b, TestUser::getId, TestUser1::getUid).and(b).like(TestUser1::getName, "小明").like(TestUser1::getName, "小明"))
                .where(a -> a.eq(TestUser::getId, "1").like(TestUser::getName, "1223"))
                .where((a, b) -> b.eq(TestUser1::getId, "x"))
//                .select((a,b) -> a.column(TestUser::getStudentName).column(TestUser::getId).use(b).column(TestUser1::getUid))
//                .groupBy(o -> o.column(TestUser::getStudentName).column(TestUser::getId))
                .groupBy((a, b) -> b.column(TestUser1::getUid).and(a).columnAll())
                .firstOrNull((a) -> a.column(TestUser::getStudentName));

    }
}
