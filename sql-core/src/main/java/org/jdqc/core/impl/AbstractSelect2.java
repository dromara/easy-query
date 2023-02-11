package org.jdqc.core.impl;

import org.jdqc.core.abstraction.lambda.SqlExpression2;
import org.jdqc.core.abstraction.lambda.SqlExpression3;
import org.jdqc.core.abstraction.metadata.EntityMetadata;
import org.jdqc.core.abstraction.sql.Select2;
import org.jdqc.core.abstraction.sql.Select3;
import org.jdqc.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.core.metadata.TableInfo;
import org.jdqc.core.query.builder.SelectTableInfo;

/**
 *
 * @FileName: AbstractSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @Created by xuejiaming
 */
public abstract  class AbstractSelect2<T1,T2> extends AbstractSelect0<T1, Select2<T1,T2>> implements Select2<T1,T2> {

    private final Select2SqlProvider<T1,T2> sqlPredicateProvider;

    public AbstractSelect2(Class<T1> t1Class, Class<T2> t2Class, SelectContext selectContext, SelectTableInfoTypeEnum selectTableInfoType) {
        super(t1Class,selectContext);
        EntityMetadata entityMetadata = selectContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);
        selectContext.addSelectTable(new SelectTableInfo(entityMetadata,selectContext.getAlias(),selectContext.getNextTableIndex(), selectTableInfoType));
        this.sqlPredicateProvider =new Select2SqlProvider<>(selectContext);
    }

    @Override
    public <T3> Select3<T1, T2, T3> leftJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        return null;
    }

    @Override
    public <T3> Select3<T1, T2, T3> innerJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        return null;
    }

    @Override
    public Select2<T1, T2> where(boolean condition, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> whereExpression) {
        if(condition){
            SqlPredicate<T1> sqlWherePredicate1 = getSelect2SqlPredicateProvider().getSqlWherePredicate1();
            SqlPredicate<T2> sqlWherePredicate2 = getSelect2SqlPredicateProvider().getSqlWherePredicate2();
            whereExpression.apply(sqlWherePredicate1,sqlWherePredicate2);
        }
        return this;
    }



    @Override
    public Select2<T1, T2> orderByAsc(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        return null;
    }

    @Override
    public Select2<T1, T2> orderByDesc(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        return null;
    }
    @Override
    public Select2<T1, T2> groupBy(boolean condition,SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        if(condition){
            SqlColumnSelector<T1> sqlGroupSelector1 = getSelect2SqlPredicateProvider().getSqlGroupSelector1();
            SqlColumnSelector<T2> sqlGroupSelector2 = getSelect2SqlPredicateProvider().getSqlGroupSelector2();
            selectExpression.apply(sqlGroupSelector1,sqlGroupSelector2);
        }
        return this;
    }
    @Override
    protected Select2<T1, T2> getSelf() {
        return this;
    }
    protected Select2SqlProvider<T1,T2> getSelect2SqlPredicateProvider(){
        return this.sqlPredicateProvider;
    }

    @Override
    protected Select1SqlProvider<T1> getSelect1SqlPredicateProvider() {
        return sqlPredicateProvider;
    }
}
