package org.easy.query.core.configuration.types;

import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: AbstractEntityQueryFilterConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:47
 * @Created by xuejiaming
 */
public abstract class AbstractGlobalQueryFilterConfiguration implements GlobalQueryFilterConfiguration {

    @Override
    public void configure(Class<?> entityClass, SqlPredicate<?> sqlPredicate) {
        if(apply(entityClass)){
            configure0(entityClass,sqlPredicate);
        }
    }
    protected abstract void configure0(Class<?> entityClass, SqlPredicate<?> sqlPredicate);
}
