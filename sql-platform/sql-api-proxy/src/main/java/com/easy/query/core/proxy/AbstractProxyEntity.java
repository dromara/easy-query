package com.easy.query.core.proxy;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.RelationEntityTableAvailable;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.proxy.columns.impl.DefaultSubquerySQLQueryableFactory;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extra.EntityExtraAutoIncludeConfigure;
import com.easy.query.core.proxy.extra.EntityExtraAutoIncludeConfigureImpl;
import com.easy.query.core.proxy.impl.SQLColumnIncludeColumn2Impl;
import com.easy.query.core.proxy.impl.SQLDraftAsSelectImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsEntryImpl;
import com.easy.query.core.proxy.impl.SQLSelectIgnoreImpl;
import com.easy.query.core.proxy.impl.SQLSelectKeysImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractBaseProxyEntity<TProxy, TEntity> {

    protected <TPropertyProxy extends SQLColumn<TProxy, TProperty>, TProperty> TPropertyProxy getValueObject(TPropertyProxy propertyProxy) {
        propertyProxy._setProxy(castChain());
        return propertyProxy;
    }

    protected String getValueProperty(String property) {
        return property;
    }

    public void or(SQLActionExpression sqlActionExpression) {
        or(true, sqlActionExpression);
    }

    public void or(boolean condition, SQLActionExpression sqlActionExpression) {
        if (condition) {
            getEntitySQLContext()._whereOr(sqlActionExpression);
        }
    }

    public void and(SQLActionExpression sqlActionExpression) {
        and(true, sqlActionExpression);
    }

    public void and(boolean condition, SQLActionExpression sqlActionExpression) {
        if (condition) {
            getEntitySQLContext()._whereAnd(sqlActionExpression);
        }
    }

    /**
     * 支持where having order
     * 请使用{@link #expression()}或者{@link Expression#sql(String)}
     *
     * @param sqlSegment
     */
    @Deprecated
    public void executeSQL(String sqlSegment) {
        executeSQL(sqlSegment, c -> {
        });
    }

    /**
     * 支持where having order
     * 请使用{@link #expression()}或者{@link Expression#sql(String, SQLActionExpression1)}
     *
     * @param sqlSegment
     * @param contextConsume
     */
    @Deprecated
    public void executeSQL(String sqlSegment, SQLActionExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        executeSQL(true, sqlSegment, contextConsume);
    }

    /**
     * 支持where having order
     * 请使用{@link #expression()}或者{@link Expression#sql(boolean, String, SQLActionExpression1)}
     *
     * @param condition
     * @param sqlSegment
     * @param contextConsume
     */
    @Deprecated
    public void executeSQL(boolean condition, String sqlSegment, SQLActionExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            getEntitySQLContext()._executeNativeSql(sqlSegment, contextConsume);
        }
    }

    protected <T, N> N __cast(T original) {
        return EasyObjectUtil.typeCastNullable(original);
    }


//    /**
//     * 返回group或者selectDraft自定义sql片段
//     * 请使用{@link #expression()}或者{@link Expression#sqlType(String)}
//     * <blockquote><pre>
//     * {@code
//     *
//     *  .select((t, t1, t2) -> new QueryVOProxy() {{
//     *      t.sql("now()");
//     *      //指定返回类型给draft类型进行明确
//     *      //t.sql("now()").setPropertyType(String.class);
//     *  }}).toList();
//     * }
//     * </blockquote></pre>
//     * @param sqlSegment
//     * @return
//     */
//    @Deprecated
//    public PropTypeColumn<Object> sql(String sqlSegment) {
//        return sql(sqlSegment, c->{});
//    }

//    /**
//     * 返回group或者selectDraft自定义sql片段
//     * 请使用{@link #expression()}或者{@link Expression#sqlType(String,SQLExpression1)}
//     * <blockquote><pre>
//     * {@code
//     *
//     *  .select((t, t1, t2) -> new QueryVOProxy() {{
//     *      t.sql("IFNull({0},{1})",c->c.expression(t.id()).value("1"));
//     *      //指定返回类型给draft类型进行明确
//     *      //t.sql("IFNull({0},{1})",c->c.expression(t.id()).value("1")).setPropertyType(String.class);
//     *  }}).toList();
//     * }
//     * </blockquote></pre>
//     * @param sqlSegment 片段
//     * @param contextConsume 片段参数
//     * @return 返回元素sql片段
//     */
//    @Deprecated
//    public PropTypeColumn<Object> sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
//        return new SQLNativeDraftImpl(entitySQLContext,(alias, f) -> {
//            f.sqlNativeSegment(sqlSegment, c -> {
//                if (alias != null) {
//                    c.setPropertyAlias(alias);
//                }
//                contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
//            });
//        });
//    }

    /**
     * 返回子查询
     * 请使用 {@link #expression()}或者{@link Expression#subQuery(SQLFuncExpression)}
     * <blockquote><pre>
     * {@code
     *      o.subQuery(()->{
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
    public <TSubQuery> PropTypeColumn<TSubQuery> subQuery(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc) {
        Query<TSubQuery> subQueryQuery = subQueryableFunc.apply();
        return new SQLDraftAsSelectImpl<>((alias, f) -> {
            f.columnSubQueryAs(() -> subQueryQuery, alias);
        }, subQueryQuery.queryClass());
    }

    /**
     * 所有主键列
     *
     * @return 选择所有主键列的表达式
     */
    public SQLSelectAsExpression columnKeys() {
        return new SQLSelectKeysImpl(this.getEntitySQLContext(), getTable());
    }

//    /**
//     * 请使用{@link #expression()}或者{@link Expression#now()}
//     *
//     * @return
//     */
//    @Deprecated
//    public ColumnFunctionComparableDateTimeChainExpression<LocalDateTime> _now() {
//        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, SQLFunc::now, LocalDateTime.class);
//    }

    /**
     * 如果当前表示关联关系表则可以选择性的设置是否逻辑删除
     *
     * @param tableLogic
     */
    public void relationLogicDelete(Supplier<Boolean> tableLogic) {
        Map<RelationTableKey, EntityTableExpressionBuilder> relationTables = entitySQLContext.getEntityExpressionBuilder().getRelationTables();
        TableAvailable tableAvailable = getTable();
        for (EntityTableExpressionBuilder value : relationTables.values()) {
            if (value.getEntityTable().equals(tableAvailable)) {
                value.setTableLogicDelete(tableLogic);
                break;
            }
        }
    }
//
//    /**
//     * 请使用{@link #expression()}或者{@link Expression#utcNow()}
//     *
//     * @return
//     */
//    @Deprecated
//    public ColumnFunctionComparableDateTimeChainExpression<LocalDateTime> _utcNow() {
//        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, SQLFunc::utcNow, LocalDateTime.class);
//    }

    /**
     * 查询表所有属性字段,如果前面已经单独查询了那么会追加下去
     *
     * <blockquote><pre>
     * {@code
     *  //选择o表所有属性
     *  .select(o->new ResultProxy().selectAll(o));
     *  //选择o表所有属性上下两种写法都可以
     *  .select(o->new ResultProxy().adapter(x->{
     *     x.selectAll(o);
     *  }));
     *  }
     * </pre></blockquote>
     *
     * @param proxy
     * @param <TRProxy>
     * @param <TREntity>
     * @return
     */
    public <TRProxy extends ProxyEntity<TRProxy, TREntity>, TREntity> TProxy selectAll(TRProxy proxy) {
        entitySQLContext.accept(new SQLSelectAllImpl(proxy.getEntitySQLContext(), proxy.getTable(), new TablePropColumn[0]));
        return castChain();
    }

    /**
     * 忽略前面所选的
     * <blockquote><pre>
     * {@code
     *  //选择o表所有属性但是忽略id和name
     *  .select(o->new ResultProxy().selectAll(o).selectIgnores(o.id(),o.name()));
     *  //选择o表所有属性但是忽略id和name 上下两种写法都可以
     *  .select(o->new ResultProxy().adapter(x->{
     *     x.selectAll(o).selectIgnores(o.id(),o.name()));
     *  }));
     *  }
     * </pre></blockquote>
     *
     * @param ignoreTableProps
     * @return
     */
    public TProxy selectIgnores(TablePropColumn... ignoreTableProps) {
        entitySQLContext.accept(new SQLSelectIgnoreImpl(ignoreTableProps));
        return castChain();
    }

    /**
     * 快速选择表达式
     * <blockquote><pre>
     * {@code
     *
     *  .select(o->new ResultProxy().selectExpression(o.id(),o.name()));
     *
     *  .select(o->new ResultProxy().adapter(x->{
     *      x.selectExpression(o.id(),o.name());
     *  }));
     *  }
     * </pre></blockquote>
     *
     * @param sqlSelectAsExpression 要查询的表达式
     */
    public TProxy selectExpression(SQLSelectAsExpression... sqlSelectAsExpression) {
        entitySQLContext.accept(sqlSelectAsExpression);
        return castChain();
    }

    /**
     * 支持动态select+动态group取列防止sql注入
     *
     * @param sqlTableOwner 要查询的表
     * @param property      要查询的属性
     */
    public TProxy selectColumn(SQLTableOwner sqlTableOwner, String property) {
        entitySQLContext.accept(new SQLSelectAsEntryImpl(this.getEntitySQLContext(), sqlTableOwner.getTable(), property));
        return castChain();
    }

    /**
     * 支持动态select+动态selectAs取列防止sql注入
     *
     * @param sqlTableOwner 要查询的表
     * @param property      要查询的属性
     * @param propertyAlias 要查询的属性别名映射到返回结果的属性名称
     */
    public TProxy selectColumnAs(SQLTableOwner sqlTableOwner, String property, String propertyAlias) {
        entitySQLContext.accept(new SQLSelectAsEntryImpl(this.getEntitySQLContext(), sqlTableOwner.getTable(), property, propertyAlias));
        return castChain();
    }


//    /**
//     * 快速选择表达式
//     * <blockquote><pre>
//     * {@code
//     *
//     *  .select(t -> {
//     *         TopicProxy topicProxy = new TopicProxy();
//     *         topicProxy.clientSelect(selector -> {
//     *             selector.column(t.getTable(), "id");
//     *             selector.column(t.getTable(), "title");
//     *         });
//     *         return topicProxy;
//     *     })
//     *  }
//     * </pre></blockquote>
//     **/
//    public void clientSelect(Consumer<AsSelector> selectorConsumer) {
//        entitySQLContext.accept(new SQLSelectAsOnlyImpl(this.getTable(),this.getEntitySQLContext(),selectorConsumer));
//    }

    /**
     * 增强当前代理对象
     *
     * @param selectExpression 选择表达式
     * @return 返回增强后的当前代理对象
     */
    public TProxy adapter(Consumer<TProxy> selectExpression) {
        selectExpression.accept(castChain());
        return castChain();
    }
    public Expression expression() {
        return Expression.of(entitySQLContext);
    }

    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> void set(TPropertyProxy columnProxy) {
        set(columnProxy, null);
    }

    /**
     * <blockquote><pre>
     * {@code
     *     new MyUserVOProxy()
     *        .vo1().set(user.name())
     *        .vo2().set(user.id())
     *        .vo3().set(user.phone())
     *        .cards().set(user.bankCards().where(bc -> bc.type().eq("储蓄卡")), (self, target) -> {
     *            self.type().set(target.code());
     *            self.code().set(target.bank().name());
     *        })
     * }
     * </pre></blockquote>
     * @param columnProxy
     * @param navigateSelectExpression
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> void set(TPropertyProxy columnProxy, SQLActionExpression2<TProxy, TPropertyProxy> navigateSelectExpression) {
        DefaultSubquerySQLQueryableFactory.dslNavigateSet(columnProxy);
        TProxy tProxy = create(null, this.getEntitySQLContext());
        getCurrentEntitySQLContext().accept(new SQLColumnIncludeColumn2Impl<>(((RelationEntityTableAvailable) columnProxy.getTable()).getOriginalTable(), columnProxy.getNavValue(), getNavValue(), columnProxy, tProxy, navigateSelectExpression));
    }

    /**
     * 判断当前对象是否不存在默认 key is null
     */
    public void isNull() {
        isNull(true);
    }

    /**
     * 判断当前对象是否不存在默认 key is null
     *
     * @param condition false不生效 true生效
     */
    public void isNull(boolean condition) {
        if (condition) {
            boolean ok = isNullOrNotNull(true);
            if (!ok) {
                throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(getTable().getEntityMetadata().getEntityClass()) + " not found any key,proxy.isNull() not support");
            }
        }
    }

    /**
     * 判断当前对象是否不存在默认 key is not null
     */
    public void isNotNull() {
        isNotNull(true);
    }

    /**
     * 判断当前对象是否不存在默认 key is not null
     *
     * @param condition false不生效 true生效
     */
    public void isNotNull(boolean condition) {
        if (condition) {
            boolean ok = isNullOrNotNull(false);
            if (!ok) {
                throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(getTable().getEntityMetadata().getEntityClass()) + " not found any key,proxy.isNotNull() not support");
            }
        }
    }

    private boolean isNullOrNotNull(boolean isNull) {

        TableAvailable tableAvailable = this.getTable();
        Collection<String> keyProperties = tableAvailable.getEntityMetadata().getKeyProperties();

        if (EasyCollectionUtil.isEmpty(keyProperties)) {

            Collection<ColumnMetadata> columns = tableAvailable.getEntityMetadata().getColumns();
            ColumnMetadata columnMetadata = EasyCollectionUtil.firstOrDefault(columns, c -> !c.isNullable(), null);
            if (columnMetadata != null) {
                getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                    if (isNull) {
                        f.isNull(tableAvailable, columnMetadata.getPropertyName());
                    } else {
                        f.isNotNull(tableAvailable, columnMetadata.getPropertyName());
                    }
                }));
                return true;
            }
        } else {
            String key = EasyCollectionUtil.first(keyProperties);
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                if (isNull) {
                    f.isNull(tableAvailable, key);
                } else {
                    f.isNotNull(tableAvailable, key);
                }
            }));
            return true;
        }
        return false;
    }

    /**
     * 仅selectAutoInclude这个api会生效用于增强查询结果
     * @return
     */
    public EntityExtraAutoIncludeConfigure<TProxy,TEntity> EXTRA_AUTO_INCLUDE_CONFIGURE(){
        return new EntityExtraAutoIncludeConfigureImpl<>();
    }
}
