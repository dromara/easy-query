package org.easy.query.core.impl.lambda;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;
import org.easy.query.core.util.LambdaUtil;
import org.easy.query.core.abstraction.sql.base.ColumnSelector;

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

    public AbstractSqlColumnSelector(int index, SelectContext selectContext, SqlSegment sqlSegment){
        super(sqlSegment);
        this.index = index;

        this.selectContext = selectContext;
    }
    @Override
    public  int index(){
        return this.index;
    }

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
