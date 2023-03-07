package org.easy.query.core.configuration.types;

import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: EntityQueryFilterConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:25
 * @Created by xuejiaming
 */
public interface GlobalQueryFilterConfiguration {
    String queryFilterName();
    boolean apply(Class<?> entityClass);
    void configure(Class<?> entityClass, SqlPredicate<?> sqlPredicate);
}
