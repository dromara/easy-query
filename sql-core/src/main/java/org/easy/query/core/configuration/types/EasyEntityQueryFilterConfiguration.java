package org.easy.query.core.configuration.types;

import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.util.EasyUtil;

/**
 * @FileName: EasyEntityQueryFilterConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:29
 * @Created by xuejiaming
 */
//public class EasyEntityQueryFilterConfiguration extends AbstractGlobalQueryFilterConfiguration {
//    private Property<Object,?> tenantProperty;
//    @Override
//    public boolean apply(Class<?> entityClass) {
//        return GlobalQueryFilterConfiguration.class.isAssignableFrom(entityClass);
//    }
//
//    @Override
//    protected void configure0(Class<?> entityClass, SqlPredicate<Object> sqlPredicate) {
//
//        if(this.tenantProperty==null){
//            tenantProperty = EasyUtil.getLambdaProperty(entityClass, "tenantId", String.class);
//        }
//
//        sqlPredicate.eq(tenantProperty,"123");
//    }
//}
