package org.jdqc.sql.core.impl.lambda;

import org.jdqc.sql.core.abstraction.lambda.Property;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelectColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SelectColumnSelector;
import org.jdqc.sql.core.impl.SelectContext;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @Created by xuejiaming
 */
public class DefaultSqlSelector<T1,TR> extends AbstractSqlColumnSelector<T1, SqlSelectColumnSelector<T1, TR>> implements SqlSelectColumnSelector<T1,TR> {

    public DefaultSqlSelector(int index, SelectContext selectContext){
        super(index,selectContext);

    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnAll() {
        return this;
    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnIgnore(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnCount(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnSum(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnMax(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnMin(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnAvg(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelectColumnSelector<T1, TR> columnLen(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <T2, TChain2> SelectColumnSelector<T2, TR, TChain2> use(SelectColumnSelector<T2, TR, TChain2> sub) {
        return sub;
    }

    @Override
    public StringBuilder getSql() {
        return this.getSelectContext().getSelect();
    }
}
