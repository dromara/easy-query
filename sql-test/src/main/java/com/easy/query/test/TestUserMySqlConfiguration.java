//package org.easy.test;
//
//import com.easy.query.core.configuration.types.AbstractEntityTypeConfiguration;
//import com.easy.query.core.configuration.types.EntityTypeBuilder;
//
//import java.time.LocalDateTime;
//
///**
// * @FileName: TestUserMySqlConfiguration.java
// * @Description: 文件说明
// * @Date: 2023/2/27 08:29
// * @Created by xuejiaming
// */
//public class TestUserMySqlConfiguration extends AbstractEntityTypeConfiguration<TestUserMysql> {
//    @Override
//    public void configure(EntityTypeBuilder<TestUserMysql> builder) {
//        builder.configLogicDelete(o->o.isNull(TestUserMysql::getDeleteAt),x->x.set(TestUserMysql::getDeleteAt, LocalDateTime.now()));
//    }
//}
