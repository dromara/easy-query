package org.easy.test;

import org.easy.query.core.configuration.AbstractEntityTypeConfiguration;
import org.easy.query.core.configuration.EntityTypeBuilder;

/**
 * @FileName: TestUserMySqlConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/2/27 08:29
 * @Created by xuejiaming
 */
public class TestUserMySqlConfiguration extends AbstractEntityTypeConfiguration<TestUserMysql> {
    @Override
    public void configure(EntityTypeBuilder<TestUserMysql> builder) {
        builder.configLogicDelete(o->o.isNull(TestUserMysql::getDeleteAt),x->x.isNotNull(TestUserMysql::getDeleteAt));
    }
}
