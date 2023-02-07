package org.jdqc.sql.core.impl.lambda;

import org.jdqc.sql.core.abstraction.lambda.Property;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelector0;
import org.jdqc.sql.core.impl.SelectContext;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @Created by xuejiaming
 */
public class DefaultSqSelector<T1,TR> implements SqlSelector<T1,TR> {
    private final int index;
    private final SelectContext selectContext;

    public DefaultSqSelector(int index, SelectContext selectContext){

        this.index = index;
        this.selectContext = selectContext;
    }

    @Override
    public int index() {
        return index;
    }
    @Override
    public SqlSelector<T1, TR> selectAll() {
        return this;
    }

    @Override
    public SqlSelector<T1, TR> select(Property<T1, ?> column) {
        SelectTableInfo table = selectContext.getTable(index);
        String columnName = table.getTable().getColumnName(column);
        String s = table.getAlias() + "." + columnName;
        System.out.println("select "+s);
        return this;
    }

    @Override
    public SqlSelector<T1, TR> selectIgnore(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> selectAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> selectCount(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> selectSum(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> selectMax(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> selectMin(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> selectAvg(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> selectLen(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <T2, TChain2> SqlSelector0<T2, TR, TChain2> and(SqlSelector0<T2, TR, TChain2> sub) {
        return null;
    }
}
