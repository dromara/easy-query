package org.easy.query.core.impl;

import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.sql.Insert;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;

import java.util.Collection;

/**
 * @FileName: AbstractInsert.java
 * @Description: 文件说明
 * @Date: 2023/2/20 12:30
 * @Created by xuejiaming
 */
public class AbstractInsert<T> implements Insert<T> {
    @Override
    public Insert<T> insert(T entity) {
        return null;
    }

    @Override
    public Insert<T> insert(Collection<T> entities) {
        return null;
    }

    @Override
    public Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression) {
        return null;
    }

    @Override
    public long execute() {
        return 0;
    }

    @Override
    public String toSql() {
        return null;
    }
}
