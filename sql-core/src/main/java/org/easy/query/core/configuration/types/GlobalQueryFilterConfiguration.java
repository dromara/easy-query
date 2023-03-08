package org.easy.query.core.configuration.types;

import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: EntityQueryFilterConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:25
 * @Created by xuejiaming
 */
public interface GlobalQueryFilterConfiguration {
    /**
     * query filter名称
     * @return
     */
    String queryFilterName();

    /**
     * 是否接受对应对象字节
     * @param entityClass
     * @return
     */
    boolean apply(Class<?> entityClass);

    /**
     * 配置
     * @param entityClass
     * @param sqlPredicate
     */
    void configure(Class<?> entityClass, SqlPredicate<?> sqlPredicate);
}
