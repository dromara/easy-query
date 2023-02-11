package org.jdqc.core.impl.lambda;

import org.jdqc.core.abstraction.lambda.Property;
import org.jdqc.core.abstraction.metadata.ColumnMetadata;
import org.jdqc.core.impl.SelectContext;
import org.jdqc.core.abstraction.sql.base.ColumnSelector;
import org.jdqc.core.query.builder.SelectTableInfo;
import org.jdqc.core.util.LambdaUtil;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @Created by xuejiaming
 */
public abstract class AbstractSqlColumnSelector<T1,TChain> extends SelectorBuilder implements ColumnSelector<T1,TChain> {
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
        String attrName = LambdaUtil.getAttrName(column);
        String columnName = table.getColumnName(attrName);
        appendSelectSql(table.getAlias(),columnName);
        return (TChain) this;
    }

    @Override
    public TChain columnAll() {
        SelectTableInfo table = selectContext.getTable(index());
        Collection<ColumnMetadata> columns = table.getEntityMetadata().getColumns();
        for (ColumnMetadata column : columns) {
            appendSelectSql(table.getAlias(),column.getName());
        }
        return (TChain) this;
    }

    @Override
    public TChain columnIgnore(Property<T1, ?> column) {
        return (TChain) this;
    }

    public SelectContext getSelectContext() {
        return selectContext;
    }
}
