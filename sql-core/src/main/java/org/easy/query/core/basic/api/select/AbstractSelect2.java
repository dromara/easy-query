package org.easy.query.core.basic.api.select;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider2;
import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.lambda.SqlExpression3;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.impl.Select2SqlProvider;
import org.easy.query.core.basic.api.context.SelectContext;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 *
 * @FileName: AbstractSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @Created by xuejiaming
 */
public abstract  class AbstractSelect2<T1,T2> extends AbstractSelect0<T1, Select2<T1,T2>> implements Select2<T1,T2> {

    private final EasyQuerySqlBuilderProvider2<T1,T2> sqlPredicateProvider;

    public AbstractSelect2(Class<T1> t1Class, Class<T2> t2Class, SelectContext selectContext, MultiTableTypeEnum selectTableInfoType) {
        super(t1Class,selectContext);
        EntityMetadata entityMetadata = selectContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);
        entityMetadata.checkTable();
        int tableIndex = selectContext.getNextTableIndex();
        selectContext.addSqlTable(new SqlTableInfo(entityMetadata,selectContext.getAlias()+tableIndex,tableIndex, selectTableInfoType));
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
            SqlPredicate<T1> sqlWherePredicate1 = getSqlBuilderProvider2().getSqlWherePredicate1();
            SqlPredicate<T2> sqlWherePredicate2 = getSqlBuilderProvider2().getSqlWherePredicate2();
            whereExpression.apply(sqlWherePredicate1,sqlWherePredicate2);
        }
        return this;
    }



    @Override
    public Select2<T1, T2> orderByAsc(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        if(condition){
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider2().getSqlOrderColumnSelector1(true);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider2().getSqlOrderColumnSelector2(true);
            selectExpression.apply(sqlOrderColumnSelector1,sqlOrderColumnSelector2);
        }
        return this;
    }

    @Override
    public Select2<T1, T2> orderByDesc(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        if(condition){
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider2().getSqlOrderColumnSelector1(false);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider2().getSqlOrderColumnSelector2(false);
            selectExpression.apply(sqlOrderColumnSelector1,sqlOrderColumnSelector2);
        }
        return this;
    }
    @Override
    public Select2<T1, T2> groupBy(boolean condition,SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        if(condition){
            SqlColumnSelector<T1> sqlGroupSelector1 = getSqlBuilderProvider2().getSqlGroupColumnSelector1();
            SqlColumnSelector<T2> sqlGroupSelector2 = getSqlBuilderProvider2().getSqlGroupColumnSelector2();
            selectExpression.apply(sqlGroupSelector1,sqlGroupSelector2);
        }
        return this;
    }
    @Override
    protected Select2<T1, T2> castSelf() {
        return this;
    }

    @Override
    protected EasyQuerySqlBuilderProvider2<T1,T2> getSqlBuilderProvider1() {
        return sqlPredicateProvider;
    }
    protected EasyQuerySqlBuilderProvider2<T1,T2> getSqlBuilderProvider2() {
        return sqlPredicateProvider;
    }

}
