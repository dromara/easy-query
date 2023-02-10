package org.jdqc.core.impl.lambda;

import org.jdqc.core.impl.SelectContext;
import org.jdqc.core.abstraction.sql.base.SqlColumnSelector;

/**
 * @FileName: DefaultSqlGroupSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:42
 * @Created by xuejiaming
 */
public class DefaultSqlGroupSelector<T1> extends AbstractSqlColumnSelector<T1,SqlColumnSelector<T1>> implements SqlColumnSelector<T1> {
    public DefaultSqlGroupSelector(int index, SelectContext selectContext) {
        super(index, selectContext);
    }

    @Override
    public StringBuilder getSql() {
        return this.getSelectContext().getGroup();
    }
}
