package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression;
import org.jdqc.sql.core.abstraction.sql.Select0;
import org.jdqc.sql.core.abstraction.sql.base.ColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnAsSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;
import org.jdqc.sql.core.exception.JDQCException;
import org.jdqc.sql.core.impl.lambda.DefaultSqlColumnAsSelector;
import org.jdqc.sql.core.impl.lambda.DefaultSqlColumnSelector;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 * @Created by xuejiaming
 */
public abstract class AbstractSelect0<T1,TChain> implements Select0<T1, TChain> {
    protected final Class<T1> t1Class;
    private final SelectContext selectContext;

    public AbstractSelect0(Class<T1> t1Class,SelectContext selectContext){
        this.t1Class = t1Class;

        this.selectContext = selectContext;
    }


    @Override
    public abstract int count();

    @Override
    public abstract boolean any();

    @Override
    public  T1 firstOrNull(){
        SqlExpression<SqlColumnAsSelector<T1, T1>> selectExpression= ColumnSelector::columnAll;
        return firstOrNull(t1Class,selectExpression);
    }

    @Override
    public T1 firstOrNull(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        SqlExpression<SqlColumnAsSelector<T1, T1>> selectAsExpression= o->selectExpression.apply((SqlColumnSelector<T1>)o);
        return firstOrNull(t1Class,selectAsExpression);
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return null;
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        this.take(1);
        List<TR> list = toList(resultClass,selectExpression);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public  List<T1> toList(){
        System.out.println(toSql());
        System.out.println(getSelectContext().getParams());
        return null;
    }

    @Override
    public  String toSql(){

        if(getSelectContext().getSelect().length()==0)
        {
            SqlExpression<SqlColumnSelector<T1>> selectExpression= ColumnSelector::columnAll;
            DefaultSqlColumnSelector<T1> selector = new DefaultSqlColumnSelector<>(0, getSelectContext());
            selectExpression.apply(selector);
        }
        StringBuilder sql = new StringBuilder("SELECT " + getSelectContext().getSelect());
        int tableCount = getSelectContext().getTables().size();
        if(tableCount==0){
            throw new JDQCException("未找到查询表信息");
        }
        for (int i = 0; i < tableCount-1; i++) {
            SelectTableInfo table = getSelectContext().getTable(i);

            sql.append(table.getSelectTableSource()).append(table.getTable().getTableType().getSimpleName()).append(" ").append(table.getAlias());
            if(table.getOn().length()==0){
                break;
            }
            SelectTableInfo table1 = getSelectContext().getTable(i+1);
            sql.append(table1.getSelectTableSource()).append(" ").append(table.getTable().getTableType().getSimpleName()).append(" ").append(table1.getAlias()).append(" ON ").append(table.getOn());
        }
        if(getSelectContext().getWhere().length()>0){
            sql.append(" WHERE").append(getSelectContext().getWhere());
        }
        if(getSelectContext().getGroup().length()>0){
            sql.append(" GROUP BY ").append(getSelectContext().getGroup());
        }
        return sql.toString();
    }
    @Override
    public String toSql(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        DefaultSqlColumnSelector<T1> selector = new DefaultSqlColumnSelector<>(0, getSelectContext());
        selectExpression.apply(selector);
        return toSql();
    }

    @Override
    public <TR> String toSql(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        DefaultSqlColumnAsSelector<T1, TR> selector = new DefaultSqlColumnAsSelector<>(0, getSelectContext());
        selectExpression.apply(selector);
        return toSql();
    }
    protected abstract TChain getSelf();

    @Override
    public TChain where(boolean condition,SqlExpression<SqlPredicate<T1>> whereExpression) {
        if(condition){
            SqlPredicate<T1> sqlPredicate = getSelect1SqlPredicateProvider().getSqlPredicate1(PredicateModeEnum.WHERE_PREDICATE);
            whereExpression.apply(sqlPredicate);
        }
        return getSelf();
    }

    @Override
    public List<T1> toList(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        SqlColumnSelector<T1> selector = getSelect1SqlPredicateProvider().getSqlColumnSelector1();
        selectExpression.apply(selector);
        String s = toSql();
        System.out.println(s);
        return new ArrayList<>();
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        return null;
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {

        DefaultSqlColumnAsSelector<T1, TR> selector = new DefaultSqlColumnAsSelector<>(0, getSelectContext());
        selectExpression.apply(selector);
        String s = toSql();
        System.out.println(s);
        return new ArrayList<>();
    }
    //
//    @Override
//    public TChain select(boolean condition,SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
//        if(condition){
//            SqlColumnAsSelector<T1, TR> sqlSelector = getSelect1SqlPredicateProvider().getSqlSelector1();
//            selectExpression.apply(sqlSelector);
//        }
//        return getSelf();
//    }

    @Override
    public TChain groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        if(condition){
            SqlColumnSelector<T1> sqlGroupSelector1 = getSelect1SqlPredicateProvider().getSqlGroupSelector1();
            selectExpression.apply(sqlGroupSelector1);
        }
        return getSelf();
    }

    @Override
    public TChain orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return getSelf();
    }

    @Override
    public TChain orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return getSelf();
    }

    @Override
    public TChain skip(boolean condition, int skip) {
        if(condition){
            this.getSelectContext().setSkip(skip);
        }
        return getSelf();
    }

    @Override
    public TChain take(boolean condition, int take) {
        return getSelf();
    }


    public SelectContext getSelectContext() {
        return selectContext;
    }

    protected abstract Select1SqlProvider<T1> getSelect1SqlPredicateProvider();
}
