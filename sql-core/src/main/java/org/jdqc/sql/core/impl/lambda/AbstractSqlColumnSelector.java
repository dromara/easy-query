package org.jdqc.sql.core.impl.lambda;

import org.jdqc.sql.core.abstraction.lambda.Property;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector0;
import org.jdqc.sql.core.impl.SelectContext;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @Created by xuejiaming
 */
public abstract class AbstractSqlColumnSelector<T1,TChain> extends SelectorBuilder implements SqlColumnSelector0<T1,TChain> {
    private final int index;
    private final SelectContext selectContext;

    public AbstractSqlColumnSelector(int index,SelectContext selectContext){
        this.index = index;

        this.selectContext = selectContext;
    }
    @Override
    public  int index(){
        return this.index;
    }
    @Override
    public abstract StringBuilder getSql();

    @Override
    public TChain column(Property<T1, ?> column) {
        SelectTableInfo table = selectContext.getTable(index());
        String columnName = table.getTable().getColumnName(column);
        appendSelectSql(table.getAlias(),columnName);
        return (TChain) this;
    }

    public SelectContext getSelectContext() {
        return selectContext;
    }
}
