package com.easy.query.core.proxy.core;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
import com.easy.query.api.proxy.extension.casewhen.SQLMapExpression;
import com.easy.query.api.proxy.extension.casewhen.CaseWhenThenEntityBuilder;
import com.easy.query.api.proxy.extension.partition.AvgOverBuilder;
import com.easy.query.api.proxy.extension.partition.CountOverBuilder;
import com.easy.query.api.proxy.extension.partition.DenseRankOverBuilder;
import com.easy.query.api.proxy.extension.partition.MaxOverBuilder;
import com.easy.query.api.proxy.extension.partition.MinOverBuilder;
import com.easy.query.api.proxy.extension.partition.RankOverBuilder;
import com.easy.query.api.proxy.extension.partition.RowNumberOverBuilder;
import com.easy.query.api.proxy.extension.partition.SumOverBuilder;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelector;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelectorImpl;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.DateTimeTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.DateTimeTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.StringTypeExpressionImpl;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.util.EasySQLUtil;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2024/2/17 22:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class Expression {
    private final EntitySQLContext entitySQLContext;

    private Expression(EntitySQLContext entitySQLContext) {

        this.entitySQLContext = entitySQLContext;
    }

    public static Expression of(EntitySQLContext entitySQLContext) {
        return new Expression(entitySQLContext);
    }

    public static Expression of(EntitySQLContextAvailable entitySQLContextAvailable) {
        return new Expression(entitySQLContextAvailable.getEntitySQLContext());
    }

    /**
     * 支持where having order
     * 执行片段 直接在where having order中执行 插入片段
     * <blockquote><pre>
     * {@code
     *   List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
     *                 .where(it -> {
     *                     it.expression().rawSQLCommand("FIND_IN_SET({0},{1})", "3123456", it.idCard());
     *                 }).toList();
     *
     * }
     * </blockquote></pre>
     *
     * @param sqlTemplate sql模板参数使用{0}...{n}
     * @param parameters 模板参数
     */
    public void rawSQLCommand(String sqlTemplate, Object... parameters) {
        sql(true, sqlTemplate, c->c.parameters(parameters));
    }

    /**
     * 类型片段
     *
     * 类型片段支持比较即后续的操作
     * <blockquote><pre>
     * {@code
     *
     *  List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
     *          .where(it -> {
     *              it.expression().rawSQLStatement("SUBSTR({0},{1},{2})", it.idCard(),1,2).asStr().eq("312345");
     *          }).toList();
     *
     * }
     * </blockquote></pre>
     * @param sqlTemplate sql模板参数使用{0}...{n}
     * @param parameters 模板参数
     * @return 类型表达式
     */
    public AnyTypeExpression<Object> rawSQLStatement(String sqlTemplate, Object... parameters) {
        return sqlSegment(sqlTemplate, c->c.parameters(parameters));
    }

    /**
     * 请使用{@link #rawSQLStatement(String, Object...)}
     * @param sqlSegment
     * @return
     */
    public AnyTypeExpression<Object> sqlSegment(String sqlSegment) {
        return sqlSegment(sqlSegment, x -> {
        }, Object.class);
    }

    /**
     * 请使用{@link #rawSQLStatement(String, Object...)}
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    @Deprecated
    public AnyTypeExpression<Object> sqlSegment(String sqlSegment, SQLActionExpression1<ProxyColumnFuncSelector> contextConsume) {
        return sqlSegment(sqlSegment, contextConsume, Object.class);
    }

    /**
     * 请使用{@link #rawSQLStatement(String, Object...)}
     * 返回group或者selectDraft自定义sql片段
     * <blockquote><pre>
     * {@code
     *  .select((t, t1, t2) -> new QueryVOProxy() {{
     *      t.sqlType("IFNull({0},{1})",c->c.expression(t.id()).value("1"));
     *      //指定返回类型给draft类型进行明确
     *      //t.sqlType("IFNull({0},{1})",c->c.expression(t.id()).value("1")).setPropertyType(String.class);
     *  }}).toList();
     * }
     * </blockquote></pre>
     *
     * @param sqlSegment     片段
     * @param contextConsume 片段参数
     * @param resultClass    类型
     * @param <TR>
     * @return 返回元素sql片段
     */
    @Deprecated
    public <TR> AnyTypeExpression<TR> sqlSegment(String sqlSegment, SQLActionExpression1<ProxyColumnFuncSelector> contextConsume, Class<TR> resultClass) {
        return new AnyTypeExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.anySQLFunction(sqlSegment, c -> contextConsume.apply(new ProxyColumnFuncSelectorImpl(c)));
        }, resultClass);
    }


    /**
     * 支持where having order
     * 请使用{@link #rawSQLCommand(String, Object...)}
     *
     * @param sqlSegment
     */
    @Deprecated
    public void sql(String sqlSegment) {
        sql(sqlSegment, c -> {
        });
    }

    /**
     * 支持where having order
     * 请使用{@link #rawSQLCommand(String, Object...)}
     *
     * @param condition  是否执行
     * @param sqlSegment
     */
    @Deprecated
    public void sql(boolean condition, String sqlSegment) {
        sql(condition, sqlSegment, c -> {
        });
    }

    /**
     * 支持where having order
     * 请使用{@link #rawSQLCommand(String, Object...)}
     *
     * @param sqlSegment
     * @param contextConsume
     */
    @Deprecated
    public void sql(String sqlSegment, SQLActionExpression1<ProxyColumnFuncSelector> contextConsume) {
        sql(true, sqlSegment, contextConsume);
    }

    /**
     * 支持where having order
     * 请使用{@link #rawSQLCommand(String, Object...)}
     *
     * @param condition      是否执行
     * @param sqlSegment
     * @param contextConsume
     */
    @Deprecated
    public void sql(boolean condition, String sqlSegment, SQLActionExpression1<ProxyColumnFuncSelector> contextConsume) {
        if (condition) {
            sqlSegment(sqlSegment, contextConsume).executeSQL();
        }
    }

//    public SQLExecutor sqlExecutor(String sqlSegment){
//        return new SQLExecutor(this,sqlSegment);
//    }


    /**
     * 返回子查询
     * 请使用{@link #subQueryColumn(Query)}
     * <blockquote><pre>
     * {@code
     *      expression.subQuery(()->{
     *          return easyEntityQuery.queryable(x.class).select(x->new StringProxy(x.id()));
     *      })
     *  }
     * </pre></blockquote>
     *
     * @param subQueryableFunc 创建子查询方法
     * @param <TSubQuery>
     * @return
     */
    @Deprecated
    public <TSubQuery> AnyTypeExpression<TSubQuery> subQuery(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc) {
        Query<TSubQuery> subQueryQuery = subQueryableFunc.apply();
        return new AnyTypeExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.anySQLFunction("{0}", c -> c.subQuery(subQueryQuery));
        }, subQueryQuery.queryClass());
    }


    /**
     * 返回子查询
     * <blockquote><pre>
     * {@code
     *      expression.subQuery(easyEntityQuery.queryable(x.class).select(x->new StringProxy(x.id())))
     *      expression.subQuery(easyEntityQuery.queryable(x.class).selectColumn(x-> x.id()))
     *  }
     * </pre></blockquote>
     *
     * @param subQueryQuery 创建子查询方法
     * @param <TSubQuery>
     * @return
     */
    public <TSubQuery> AnyTypeExpression<TSubQuery> subQueryColumn(Query<TSubQuery> subQueryQuery) {
        return new AnyTypeExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.anySQLFunction("{0}", c -> c.subQuery(subQueryQuery));
        }, subQueryQuery.queryClass());
    }


    public DateTimeTypeExpression<LocalDateTime> now() {
        return new DateTimeTypeExpressionImpl<>(entitySQLContext, null, null, SQLFunc::now, LocalDateTime.class);
    }

    public DateTimeTypeExpression<LocalDateTime> utcNow() {
        return new DateTimeTypeExpressionImpl<>(entitySQLContext, null, null, SQLFunc::utcNow, LocalDateTime.class);
    }
    public NumberTypeExpression<BigDecimal> random() {
        return new NumberTypeExpressionImpl<>(entitySQLContext, null, null, SQLFunc::random, BigDecimal.class);
    }
    public <TProp> AnyTypeExpression<TProp> maxColumns(PropTypeColumn<TProp> column1,PropTypeColumn<TProp> column2,PropTypeColumn<?>... columns){
        return new AnyTypeExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.maxColumns(c -> {
                PropTypeColumn.acceptAnyValue(c, column1);
                PropTypeColumn.acceptAnyValue(c, column2);
                for (PropTypeColumn<?> column : columns) {
                    PropTypeColumn.acceptAnyValue(c, column);
                }
            });
        }, column1.getPropertyType());
    }
    public <TProp> AnyTypeExpression<TProp> minColumns(PropTypeColumn<TProp> column1,PropTypeColumn<TProp> column2,PropTypeColumn<?>... columns){
        return new AnyTypeExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.minColumns(c -> {
                PropTypeColumn.acceptAnyValue(c, column1);
                PropTypeColumn.acceptAnyValue(c, column2);
                for (PropTypeColumn<?> column : columns) {
                    PropTypeColumn.acceptAnyValue(c, column);
                }
            });
        }, column1.getPropertyType());
    }

    /**
     * COUNT(*)
     *
     * @return 返回类型为Long
     */
    public NumberTypeExpression<Long> count() {
        return new NumberTypeExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.count(c -> {
            });
        }, Long.class);
    }

    /**
     * COUNT(*)
     *
     * @return 返回类型为Integer
     */
    public NumberTypeExpression<Integer> intCount() {
        return new NumberTypeExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.count(c -> {
            });
        }, Integer.class);
    }


    /**
     * where exists(....)
     * 请使用{@link #exists(Query)}
     *
     * @param subQueryFunc 子查询创建方法
     */
    @Deprecated
    public void exists(Supplier<Query<?>> subQueryFunc) {
        exists(true, subQueryFunc);
    }

    /**
     * where exists(....)
     * 请使用{@link #exists(boolean,Query)}
     *
     * @param condition    为true是exists生效
     * @param subQueryFunc 子查询创建方法
     */
    @Deprecated
    public void exists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            entitySQLContext.getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(subQueryFunc.get())));
        }
    }

    /**
     * where not exists(....)
     * 请使用{@link #notExists(Query)}
     *
     * @param subQueryFunc 子查询创建方法
     */
    @Deprecated
    public void notExists(Supplier<Query<?>> subQueryFunc) {
        notExists(true, subQueryFunc);
    }


    /**
     * where exists(....)
     * 请使用{@link #notExists(boolean, Query)}
     *
     * @param condition    为true是not exists生效
     * @param subQueryFunc 子查询创建方法
     */
    @Deprecated
    public void notExists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            entitySQLContext.getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notExists(subQueryFunc.get())));
        }
    }



    /**
     * where exists(....)
     *
     * @param subQuery 子查询
     */
    public void exists(Query<?> subQuery) {
        exists(true, subQuery);
    }

    /**
     * where exists(....)
     *
     * @param condition    为true是exists生效
     * @param subQuery 子查询
     */
    public void exists(boolean condition, Query<?> subQuery) {
        if (condition) {
            entitySQLContext.getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(subQuery)));
        }
    }

    /**
     * where not exists(....)
     *
     * @param subQuery 子查询创建方法
     */
    public void notExists(Query<?> subQuery) {
        notExists(true, subQuery);
    }


    /**
     * where exists(....)
     *
     * @param condition    为true是not exists生效
     * @param subQuery 子查询创建方法
     */
    public void notExists(boolean condition, Query<?> subQuery) {
        if (condition) {
            entitySQLContext.getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notExists(subQuery)));
        }
    }

    public CaseWhenThenEntityBuilder caseWhen(SQLActionExpression sqlActionExpression) {
        return new CaseWhenEntityBuilder(entitySQLContext).caseWhen(sqlActionExpression);
    }

    public SQLMapExpression newMap() {
        return new SQLMapExpression(entitySQLContext, this);
    }

    public <TV, TProperty> AnyTypeExpression<TProperty> ifElse(SQLActionExpression sqlActionExpression, TV thenValue, TV elseValue) {
        return caseWhen(sqlActionExpression).then(thenValue).elseEnd(elseValue);
    }

    public StringTypeExpression<String> concat(SQLActionExpression1<ConcatExpressionSelector> stringExpressions) {
        return new StringTypeExpressionImpl<>(entitySQLContext, null, null, fx -> {
            return fx.concat(o -> {
                stringExpressions.apply(new ConcatExpressionSelectorImpl(entitySQLContext.getRuntimeContext().fx(), o));
            });
        }, String.class);
    }

    /**
     * <blockquote><pre>
     * {@code
     *  // CONCAT(?,CAST(`id_card` AS SIGNED),?) LIKE ?
     *  Expression expression = s.expression();
     *  expression.concat(
     *      expression.constant(1),
     *      s.idCard().toNumber(Integer.class),
     *      expression.constant(2)
     *  ).like(",2,");
     *    }
     * </pre></blockquote>
     *
     * @param args 表达式
     * @return
     */
    public StringTypeExpression<String> concat(Object... args) {
        return concat(x -> {
            for (Object arg : args) {

                ConcatExpressionSelector.accept(x, arg);
            }
        });
    }

    /**
     * 格式化字符串
     *
     * @param format
     * @param args
     * @return
     */
    public StringTypeExpression<String> stringFormat(String format, Object... args) {
        if (format == null) {
            throw new EasyQueryInvalidOperationException("format is null");
        }

        List<Object> argList = EasySQLUtil.parseFormat(format, args);
        return concat(x -> {
            for (Object arg : argList) {

                ConcatExpressionSelector.accept(x, arg);
            }
        });
    }

    public RowNumberOverBuilder rowNumberOver() {
        return new RowNumberOverBuilder(entitySQLContext);
    }

    public RankOverBuilder rankOver() {
        return new RankOverBuilder(entitySQLContext);
    }

    public DenseRankOverBuilder denseRankOver() {
        return new DenseRankOverBuilder(entitySQLContext);
    }

    public <TProperty> CountOverBuilder countOver(PropTypeColumn<TProperty> countColumn) {
        return new CountOverBuilder(countColumn, entitySQLContext);
    }

    public <TProperty> SumOverBuilder<TProperty> sumOver(PropTypeColumn<TProperty> countColumn) {
        return new SumOverBuilder<>(countColumn, entitySQLContext);
    }

    public <TProperty> AvgOverBuilder avgOver(PropTypeColumn<TProperty> countColumn) {
        return new AvgOverBuilder(countColumn, entitySQLContext);
    }

    public <TProperty> MaxOverBuilder<TProperty> maxOver(PropTypeColumn<TProperty> countColumn) {
        return new MaxOverBuilder<>(countColumn, entitySQLContext);
    }

    public <TProperty> MinOverBuilder<TProperty> minOver(PropTypeColumn<TProperty> countColumn) {
        return new MinOverBuilder<>(countColumn, entitySQLContext);
    }

    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> subQueryable(Class<T> entityClass) {
        SQLClientApiFactory sqlClientApiFactory = entitySQLContext.getRuntimeContext().getSQLClientApiFactory();
        ClientQueryable<T> queryable = sqlClientApiFactory.createSubQueryable(entityClass, entitySQLContext.getRuntimeContext(),entitySQLContext.getExpressionContext());
        TProxy tProxy = EntityQueryProxyManager.create(entityClass);
        EasyEntityQueryable<TProxy, T> tProxyTEasyEntityQueryable = new EasyEntityQueryable<>(tProxy, queryable);
        tProxyTEasyEntityQueryable.get1Proxy().getEntitySQLContext().setContextHolder(this.entitySQLContext.getContextHolder());
        return tProxyTEasyEntityQueryable;
    }


    public BooleanTypeExpression<Boolean> valueOf(SQLActionExpression sqlActionExpression) {
        EntityExpressionBuilder entityExpressionBuilder = this.entitySQLContext.getEntityExpressionBuilder();
        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(true);
        FilterImpl filter = new FilterImpl(entityExpressionBuilder.getRuntimeContext(), entityExpressionBuilder.getExpressionContext(), andPredicateSegment, false, entityExpressionBuilder.getExpressionContext().getValueFilter());
        this.entitySQLContext.getCurrentEntitySQLContext()._where(filter, sqlActionExpression);

        return new BooleanTypeExpressionImpl<>(entitySQLContext, null, null, f -> f.booleanSQLFunction("({0})", c -> {
            c.expression(andPredicateSegment);
        }), Boolean.class);
    }

    public void not(SQLActionExpression sqlActionExpression) {
        EntityExpressionBuilder entityExpressionBuilder = this.entitySQLContext.getEntityExpressionBuilder();
        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(true);
        FilterImpl filter = new FilterImpl(entityExpressionBuilder.getRuntimeContext(), entityExpressionBuilder.getExpressionContext(), andPredicateSegment, false, entityExpressionBuilder.getExpressionContext().getValueFilter());
        this.entitySQLContext.getCurrentEntitySQLContext()._where(filter, sqlActionExpression);
        SQLFunction notFunction = this.entitySQLContext.getRuntimeContext().fx().not(c -> c.expression(andPredicateSegment));
        this.sql("{0}", c -> {
            c.sqlFunc(notFunction);
        });

    }


    public DateTimeTypeExpression<LocalDateTime> constant(LocalDateTime val) {
        return new DateTimeTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), LocalDateTime.class);
    }

    public DateTimeTypeExpression<LocalDate> constant(LocalDate val) {
        return new DateTimeTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), LocalDate.class);
    }

    public DateTimeTypeExpression<LocalTime> constant(LocalTime val) {
        return new DateTimeTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), LocalTime.class);
    }

    public DateTimeTypeExpression<Date> constant(Date val) {
        return new DateTimeTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Date.class);
    }

    public DateTimeTypeExpression<java.sql.Date> constant(java.sql.Date val) {
        return new DateTimeTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), java.sql.Date.class);
    }

    public DateTimeTypeExpression<Time> constant(Time val) {
        return new DateTimeTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Time.class);
    }

    public DateTimeTypeExpression<Timestamp> constant(Timestamp val) {
        return new DateTimeTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Timestamp.class);
    }

    public StringTypeExpression<String> constant(String val) {
        return new StringTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), String.class);
    }

    public <TNumber extends Number> NumberTypeExpression<TNumber> constant(Number val) {
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), BigDecimal.class);
    }

    public NumberTypeExpression<BigDecimal> constant(BigDecimal val) {
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), BigDecimal.class);
    }

    public NumberTypeExpression<Double> constant(Double val) {
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Double.class);
    }

    public NumberTypeExpression<Float> constant(Float val) {
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Float.class);
    }

    public NumberTypeExpression<Long> constant(Long val) {
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Long.class);
    }

    public NumberTypeExpression<Integer> constant(Integer val) {
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Integer.class);
    }

    public NumberTypeExpression<Short> constant(Short val) {
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Short.class);
    }

    public NumberTypeExpression<Byte> constant(Byte val) {
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Byte.class);
    }

    public BooleanTypeExpression<Boolean> constant(Boolean val) {
        return new BooleanTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), Boolean.class);
    }

    public <TProperty> AnyTypeExpression<TProperty> constant(TProperty val, Class<TProperty> clazz) {
        return new AnyTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(val), clazz);
    }

    public <TProperty> AnyTypeExpression<TProperty> constantOfNull() {
        return new AnyTypeExpressionImpl<>(this.entitySQLContext, null, null, f -> f.constValue(0), Object.class);
    }

}
