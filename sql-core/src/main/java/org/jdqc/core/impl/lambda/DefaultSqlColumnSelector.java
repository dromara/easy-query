package org.jdqc.core.impl.lambda;

import org.jdqc.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.core.impl.SelectContext;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/9 13:07
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSelector<T1> extends AbstractSqlColumnSelector<T1, SqlColumnSelector<T1>> implements SqlColumnSelector<T1> {
    private final StringBuilder sqlBuilder;

    public DefaultSqlColumnSelector(int index, SelectContext selectContext,StringBuilder sqlBuilder) {
        super(index, selectContext);
        this.sqlBuilder = sqlBuilder;
    }

    @Override
    public StringBuilder getSql() {
        return sqlBuilder;
    }
}