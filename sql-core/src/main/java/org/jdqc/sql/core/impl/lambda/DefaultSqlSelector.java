package org.jdqc.sql.core.impl.lambda;

import org.jdqc.sql.core.abstraction.lambda.Property;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector2;
import org.jdqc.sql.core.impl.SelectContext;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @Created by xuejiaming
 */
public class DefaultSqlSelector<T1,TR> extends AbstractSqlColumnSelector<T1,SqlSelector<T1, TR>> implements SqlSelector<T1,TR> {

    public DefaultSqlSelector(int index, SelectContext selectContext){
        super(index,selectContext);

    }

    @Override
    public SqlSelector<T1, TR> columnAll() {
        return this;
    }

    @Override
    public SqlSelector<T1, TR> columnIgnore(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> columnCount(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> columnSum(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> columnMax(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> columnMin(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> columnAvg(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlSelector<T1, TR> columnLen(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <T2, TChain2> SqlColumnSelector2<T2, TR, TChain2> and(SqlColumnSelector2<T2, TR, TChain2> sub) {
        return null;
    }

    @Override
    public StringBuilder getSql() {
        return this.getSelectContext().getSelect();
    }
}
