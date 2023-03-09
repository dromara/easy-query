package org.easy.query.core.basic.api.select.abstraction;

import org.easy.query.core.abstraction.*;
import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.select.Queryable2;
import org.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.exception.EasyQueryNotFoundException;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.abstraction.sql.DefaultPageResult;
import org.easy.query.core.abstraction.sql.PageResult;
import org.easy.query.core.abstraction.sql.enums.EasyAggregate;
import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import org.easy.query.core.expression.segment.SelectConstSegment;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.query.*;
import org.easy.query.core.util.ArrayUtil;
import org.easy.query.core.util.EasyUtil;
import org.easy.query.core.util.SqlExpressionUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 * @Created by xuejiaming
 */
public abstract class AbstractQueryable<T1> implements Queryable<T1> {
    protected final Class<T1> t1Class;
    //    protected final SqlEntityTableExpression sqlTable;
    protected final SqlEntityQueryExpression sqlEntityExpression;
//    protected final Select1SqlProvider<T1> sqlPredicateProvider;

    @Override
    public Class<T1> queryClass() {
        return t1Class;
    }

    public AbstractQueryable(Class<T1> t1Class, SqlEntityQueryExpression sqlEntityExpression) {
        this.t1Class = t1Class;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public Queryable<T1> cloneQueryable() {
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().cloneQueryable(this);
    }

    @Override
    public long count() {
        List<Long> result = cloneQueryable().select(" COUNT(1) ").toList(Long.class);
        if (result.isEmpty()) {
            return 0L;
        }
        Long r = result.get(0);
        if (r == null) {
            return 0L;
        }
        return r;
    }

    @Override
    public long countDistinct(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        ProjectSqlBuilderSegment sqlSegmentBuilder = new ProjectSqlBuilderSegment();
        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(sqlSegmentBuilder);
        selectExpression.apply(sqlColumnSelector);
        List<Long> result = cloneQueryable().select(EasyAggregate.COUNT_DISTINCT.getFuncColumn(sqlSegmentBuilder.toSql())).toList(Long.class);

        if (result.isEmpty()) {
            return 0L;
        }
        Long r = result.get(0);
        if (r == null) {
            return 0L;
        }
        return r;
    }

    @Override
    public boolean any() {
        List<Integer> result = cloneQueryable().limit(1).select(" 1 ").toList(Integer.class);
        return !result.isEmpty();
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Property<T1, TMember> column, BigDecimal def) {
        List<TMember> result = selectAggregateList(column, EasyAggregate.SUM);
        TMember resultMember = ArrayUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(Property<T1, TMember> column, TMember def) {
        List<TMember> result = selectAggregateList(column, EasyAggregate.SUM);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember maxOrDefault(Property<T1, TMember> column, TMember def) {

        List<TMember> result = selectAggregateList(column, EasyAggregate.MAX);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember minOrDefault(Property<T1, TMember> column, TMember def) {
        List<TMember> result = selectAggregateList(column, EasyAggregate.MIN);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember extends Number> TMember avgOrDefault(Property<T1, TMember> column, TMember def) {
        List<TMember> result = selectAggregateList(column, EasyAggregate.AVG);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public Integer lenOrDefault(Property<T1, ?> column, Integer def) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(0);
        String propertyName = table.getPropertyName(column);
        String ownerColumn = sqlEntityExpression.getSqlOwnerColumn(table, propertyName);
        List<Integer> result = cloneQueryable().select(EasyAggregate.LEN.getFuncColumn(ownerColumn)).toList(Integer.class);
        return ArrayUtil.firstOrDefault(result,def);
    }

    private <TMember> List<TMember> selectAggregateList(Property<T1, ?> column, IEasyFunc easyFunc) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(0);
        String propertyName = table.getPropertyName(column);
        ColumnMetadata columnMetadata = EasyUtil.getColumnMetadata(table, propertyName);
        String ownerColumn = sqlEntityExpression.getSqlOwnerColumn(table, propertyName);
        return cloneQueryable().select(easyFunc.getFuncColumn(ownerColumn)).toList((Class<TMember>)columnMetadata.getProperty().getPropertyType());
    }


    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        List<TR> list =cloneQueryable().limit(1).toList(resultClass);

        return ArrayUtil.firstOrNull(list);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        TR result = firstOrNull(resultClass);
        if(result==null){
            throw new EasyQueryNotFoundException(msg,code);
        }
        return result;
    }

    //
//    @Override
//    public T1 firstOrNull(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
//        this.limit(1);
//        List<T1> list = toList(selectExpression);
//        if (list.isEmpty()) {
//            return null;
//        }
//        return list.get(0);
//    }
//
//    @Override
//    public <TR> TR firstOrNull(Class<TR> resultClass) {
//        SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression = getDefaultColumnAsAll();
//        return firstOrNull(resultClass, selectExpression);
//    }
//
//    @Override
//    public <TR> TR firstOrNull(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
//        this.limit(1);
//        List<TR> list = toList(resultClass, selectExpression);
//        if (list.isEmpty()) {
//            return null;
//        }
//        return list.get(0);
//    }

    /**
     * 存在多张表或者不存在匿名表
     * @return
     */
    private boolean moreTableExpressionOrNoAnonymous() {
        return sqlEntityExpression.getTables().size() != 1 || !(sqlEntityExpression.getTables().get(0) instanceof AnonymousEntityTableExpression);
    }

    @Override
    public List<T1> toList() {
        return toList(queryClass());
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        return toInternalList(resultClass);
    }

    @Override
    public <TR> String toSql(Class<TR> resultClass) {

        if (SqlExpressionUtil.shouldCloneSqlEntityQueryExpression(sqlEntityExpression)) {
            return select(resultClass).toSql();
        }
        return toInternalSql();
    }
    protected abstract String toInternalSql();

    /**
     * 子类实现方法
     *
     * @return
     */
    protected <TR> List<TR> toInternalList(Class<TR> resultClass) {
        String sql = toSql(resultClass);
        EasyExecutor easyExecutor = sqlEntityExpression.getRuntimeContext().getEasyExecutor();
        return easyExecutor.query(ExecutorContext.create(sqlEntityExpression.getRuntimeContext()), resultClass, sql, sqlEntityExpression.getParameters());
    }

    /**
     * 只有select操作运行操作当前projects
     * @param selectExpression
     * @return
     */
    @Override
    public Queryable<T1> select(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(sqlEntityExpression.getProjects());
        selectExpression.apply(sqlColumnSelector);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(queryClass(), sqlEntityExpression);
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        SqlColumnAsSelector<T1, TR> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnAsSelector1(sqlEntityExpression.getProjects());
        selectExpression.apply(sqlColumnSelector);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, sqlEntityExpression);
    }

    @Override
    public Queryable<T1> select(String columns) {
        sqlEntityExpression.getProjects().getSqlSegments().clear();
        sqlEntityExpression.getProjects().append(new SelectConstSegment(columns));
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass) {
        SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression = ColumnSelector::columnAll;
        SqlColumnAsSelector<T1, TR> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnAsSelector1(sqlEntityExpression.getProjects());
        selectExpression.apply(sqlColumnSelector);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, sqlEntityExpression);
    }

//    @Override
//    public <TR> Queryable<TR> select(Class<TR> resultClass, String columns) {
//        return null;
//    }
    //    @Override
//    public String toSql() {
//        SqlExpression<SqlColumnSelector<T1>> selectExpression = ColumnSelector::columnAll;
//        return toSql(selectExpression);
//    }
//
//    @Override
//    public String toSql(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
//        ProjectSqlBuilderSegment sqlSegmentBuilder = new ProjectSqlBuilderSegment();
//        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(sqlSegmentBuilder);
//        selectExpression.apply(sqlColumnSelector);
//        return toSql(sqlSegmentBuilder.toSql());
//    }
//
//    @Override
//    public <TR> String toSql(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
//        ProjectSqlBuilderSegment sqlSegmentBuilder = new ProjectSqlBuilderSegment();
//        SqlColumnAsSelector<T1,TR> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnAsSelector1(sqlSegmentBuilder);
//        selectExpression.apply(sqlColumnSelector);
//        return toSql(sqlSegmentBuilder.toSql());
//    }
//
//    @Override
//    public abstract String toSql(String columns);

    @Override
    public Queryable<T1> where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression) {
        if (condition) {
            SqlPredicate<T1> sqlPredicate = getSqlBuilderProvider1().getSqlWherePredicate1();
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlPredicate = getSqlBuilderProvider1().getSqlGroupColumnSelector1();
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> having(boolean condition, SqlExpression<SqlAggregatePredicate<T1>> predicateExpression) {

        if (condition) {
            SqlAggregatePredicate<T1> sqlAggregatePredicate = getSqlBuilderProvider1().getSqlAggregatePredicate1();
            predicateExpression.apply(sqlAggregatePredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlPredicate = getSqlBuilderProvider1().getSqlOrderColumnSelector1(true);
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlPredicate = getSqlBuilderProvider1().getSqlOrderColumnSelector1(false);
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> limit(boolean condition, long offset, long rows) {
        if (condition) {
            sqlEntityExpression.setOffset(offset);
            sqlEntityExpression.setRows(rows);
        }
        return this;
    }

    @Override
    public PageResult<T1> toPageResult(long pageIndex, long pageSize) {
        return toPageResult(pageIndex, pageSize, t1Class);
    }

    @Override
    public <TR> PageResult<TR> toPageResult(long pageIndex, long pageSize, Class<TR> clazz) {
//        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(selectContext.getProjects());
        SqlExpression<SqlColumnSelector<T1>> selectExpression = ColumnSelector::columnAll;
//        selectExpression.apply(sqlColumnSelector);
        return doPageResult(pageIndex, pageSize, clazz, selectExpression);
    }

    @Override
    public PageResult<T1> toPageResult(long pageIndex, long pageSize, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return doPageResult(pageIndex, pageSize, t1Class, selectExpression);
    }

    protected <TR> PageResult<TR> doPageResult(long pageIndex, long pageSize, Class<TR> clazz, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        //设置每次获取多少条
        long take = pageSize <= 0 ? 1 : pageSize;
        //设置当前页码最小1
        long index = pageIndex <= 0 ? 1 : pageIndex;
        //需要跳过多少条
        long offset = (index - 1) * take;
        long total = this.count();
        if (total <= offset) {
            return new DefaultPageResult<TR>(total, new ArrayList<>(0));
        }//获取剩余条数
        long remainingCount = total - offset;
        //当剩余条数小于take数就取remainingCount
        long realTake = Math.min(remainingCount, take);
        this.limit(offset, realTake);
        sqlEntityExpression.getProjects().getSqlSegments().clear();
        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(sqlEntityExpression.getProjects());
        selectExpression.apply(sqlColumnSelector);
        List<TR> data = this.toInternalList(clazz);
        return new DefaultPageResult<TR>(total, data);
    }

    @Override
    public <TR> PageResult<TR> toPageResult(long pageIndex, long pageSize, Class<TR> clazz, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {

        ProjectSqlBuilderSegment sqlSegmentBuilder = new ProjectSqlBuilderSegment();
        SqlColumnAsSelector<T1, TR> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnAsSelector1(sqlSegmentBuilder);
        selectExpression.apply(sqlColumnSelector);
        return toPageResult(pageIndex, pageSize, clazz);
    }

    @Override
    public SqlEntityQueryExpression getSqlEntityExpression() {
        return sqlEntityExpression;
    }
    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        //todo 需要判断当前的表达式是否存在where group order之类的操作,是否是一个clear expression如果不是那么就需要先select all如果没有select过然后创建一个anonymous的table去join
        //简单理解就是queryable需要支持join操作 还有queryable 和queryable之间如何join

        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public Queryable<T1> disableLogicDelete() {
        sqlEntityExpression.getSqlExpressionContext().disableLogicDelete();
        return this;
    }

    @Override
    public Queryable<T1> enableLogicDelete() {
        sqlEntityExpression.getSqlExpressionContext().enableLogicDelete();
        return this;
    }

    @Override
    public Queryable<T1> noQueryFilter() {
        sqlEntityExpression.getSqlExpressionContext().noQueryFilter();
        return this;
    }

    @Override
    public Queryable<T1> useQueryFilter() {
        sqlEntityExpression.getSqlExpressionContext().useQueryFilter();
        return this;
    }

    @Override
    public abstract EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1();
}
