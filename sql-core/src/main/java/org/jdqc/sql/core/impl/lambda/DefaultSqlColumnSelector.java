package org.jdqc.sql.core.impl.lambda;

import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.impl.SelectContext;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/9 13:07
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSelector<T1> extends AbstractSqlColumnSelector<T1, SqlColumnSelector<T1>> implements SqlColumnSelector<T1> {
    public DefaultSqlColumnSelector(int index, SelectContext selectContext) {
        super(index, selectContext);
    }

    @Override
    public StringBuilder getSql() {
        return this.getSelectContext().getSelect();
    }
}