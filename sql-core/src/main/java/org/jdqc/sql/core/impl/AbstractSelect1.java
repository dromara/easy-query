package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression2;
import org.jdqc.sql.core.abstraction.sql.Select1;
import org.jdqc.sql.core.abstraction.sql.Select2;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;
import org.jdqc.sql.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.sql.core.query.builder.SelectTableInfo;
import org.jdqc.sql.core.schema.TableInfo;

/**
 *
 * @FileName: AbstractSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @Created by xuejiaming
 */
public abstract class AbstractSelect1<T1> extends AbstractSelect0<T1,Select1<T1>> implements Select1<T1> {

    private final Select1SqlProvider<T1> sqlPredicateProvider;
    public AbstractSelect1(Class<T1> t1Class,SelectContext selectContext) {
        super(selectContext);
        TableInfo tableInfo = selectContext.getJdqcConfiguration().getTableByEntity(t1Class);
        selectContext.addSelectTable(new SelectTableInfo(tableInfo,selectContext.getAlias(),selectContext.getNextTableIndex(), SelectTableInfoTypeEnum.FROM));
        sqlPredicateProvider= new Select1SqlProvider<>(selectContext);
    }
    @Override
    public <T2> Select2<T1, T2> leftJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        SelectContext selectContext = getSelectContext();
        AbstractSelect2<T1, T2> select2 = createSelect2(joinClass, selectContext, SelectTableInfoTypeEnum.LEFT_JOIN);
        SqlPredicate<T1> on1 = select2.getSelect2SqlPredicateProvider().getSqlPredicate1(PredicateModeEnum.ON_PREDICATE);
        SqlPredicate<T2> on2 = select2.getSelect2SqlPredicateProvider().getSqlPredicate2(PredicateModeEnum.ON_PREDICATE);
        on.apply(on1,on2);
        return select2;
    }


    @Override
    public <T2> Select2<T1, T2> innerJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        SelectContext selectContext = getSelectContext();
        AbstractSelect2<T1, T2> select2 = createSelect2(joinClass, selectContext, SelectTableInfoTypeEnum.INNER_JOIN);
        SqlPredicate<T1> sqlOnPredicate1 = select2.getSelect2SqlPredicateProvider().getSqlOnPredicate1();
        SqlPredicate<T2> sqlOnPredicate2 = select2.getSelect2SqlPredicateProvider().getSqlOnPredicate2();
        on.apply(sqlOnPredicate1,sqlOnPredicate2);
        return select2;
    }
    @Override
    protected Select1<T1> getSelf() {
        return this;
    }
    protected abstract <T2> AbstractSelect2<T1, T2> createSelect2(Class<T2> joinClass,SelectContext selectContext,SelectTableInfoTypeEnum selectTableInfoType);

    @Override
    protected Select1SqlProvider<T1> getSelect1SqlPredicateProvider(){
        return this.sqlPredicateProvider;
    }
}
