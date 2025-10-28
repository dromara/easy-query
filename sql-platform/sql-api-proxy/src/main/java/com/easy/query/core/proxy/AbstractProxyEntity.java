package com.easy.query.core.proxy;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.extension.partition.NextBuilder;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.RelationEntityTableAvailable;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.proxy.columns.impl.DefaultSubquerySQLQueryableFactory;
import com.easy.query.core.proxy.configurer.DefaultTableConfigurer;
import com.easy.query.core.proxy.configurer.TableConfigurer;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extra.EntityExtraAutoIncludeConfigure;
import com.easy.query.core.proxy.extra.EntityExtraAutoIncludeConfigureImpl;
import com.easy.query.core.proxy.impl.SQLColumnIncludeColumn2Impl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsEntryImpl;
import com.easy.query.core.proxy.impl.SQLSelectIgnoreImpl;
import com.easy.query.core.proxy.impl.SQLSelectKeysImpl;
import com.easy.query.core.proxy.sql.EasyIncludeQueryable;
import com.easy.query.core.proxy.sql.IncludeQueryable;
import com.easy.query.core.proxy.sql.include.NavigatePathAvailable;
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
public abstract class AbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractBaseProxyEntity<TProxy, TEntity> implements NavigatePathAvailable<TProxy,TEntity> {

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
     * 对目标表进行额外筛选，如果目标表不是关联关系表则和普通where一样，如果是关联关系表则在join on上添加额外条件
     * @param filterExpression
     */
    public void filter(SQLActionExpression1<TProxy> filterExpression) {
        TableAvailable thisTable = getTable();
        boolean isRelationTable = thisTable instanceof RelationEntityTableAvailable;
        if (isRelationTable) {//处理relation的join on条件
            RelationTableKey relationTableKey = ((RelationEntityTableAvailable) thisTable).getRelationTableKey();
            EntityTableExpressionBuilder entityTableExpressionBuilder = entitySQLContext.getEntityExpressionBuilder().getRelationTables().get(relationTableKey);
            if (entityTableExpressionBuilder == null) {
                throw new EasyQueryInvalidOperationException("can not find relation table for " + EasyClassUtil.getSimpleName(getTable().getEntityClass()) + ", field:" + getValue());
            }
            AndPredicateSegment filterPredicate = new AndPredicateSegment(true);
            FilterImpl onFilter = new FilterImpl(entitySQLContext.getRuntimeContext(), entitySQLContext.getExpressionContext(), filterPredicate, false, entitySQLContext.getExpressionContext().getValueFilter());
            getEntitySQLContext()._where(onFilter, () -> {
                filterExpression.apply(EasyObjectUtil.typeCastNullable(this));
            });
            PredicateSegment on = entityTableExpressionBuilder.getOn();
            PredicateSegment filterOn = entityTableExpressionBuilder.getFilterOn();
            on.addPredicateSegment(filterPredicate);
            filterOn.addPredicateSegment(filterPredicate);
        } else {
            //如果不是关联关系表在on环境下就处理join on  其他情况都放到where里面
            filterExpression.apply(EasyObjectUtil.typeCastNullable(this));
        }
//        throw new EasyQueryInvalidOperationException("can not use extraFilter for " + EasyClassUtil.getSimpleName(getTable().getEntityClass()));
    }

    public void configure(SQLActionExpression1<TableConfigurer<TProxy, TEntity>> configurer) {
        configurer.apply(new DefaultTableConfigurer<>(entitySQLContext, castChain()));
    }

    protected <T, N> N __cast(T original) {
        return EasyObjectUtil.typeCastNullable(original);
    }

    /**
     * 如果当前表示关联关系表则可以选择性的设置是否逻辑删除
     *
     * <blockquote><pre>
     * {@code
     *    .where(s -> {
     *           //对s表进行逻辑删除禁用
     *           s.configure(x->x.disableLogicDelete());
     *     })
     *  }
     * </pre></blockquote>
     *
     * @param tableLogic
     */
    @Deprecated
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
        entitySQLContext.accept(new SQLSelectIgnoreImpl(this.getTable(), ignoreTableProps));
        return castChain();
    }

    /**
     * 快速选择表达式
     * 请使用{@link #selectBy(SQLSelectAsExpression...)}
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
    @Deprecated
    public TProxy selectExpression(SQLSelectAsExpression... sqlSelectAsExpression) {
        entitySQLContext.accept(sqlSelectAsExpression);
        return castChain();
    }
    public TProxy selectBy(SQLSelectAsExpression... sqlSelectAsExpression) {
        entitySQLContext.accept(sqlSelectAsExpression);
        return castChain();
    }


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
     *
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
     *
     * @return
     */
    public EntityExtraAutoIncludeConfigure<TProxy, TEntity> EXTRA_AUTO_INCLUDE_CONFIGURE() {
        return new EntityExtraAutoIncludeConfigureImpl<>();
    }

    /**
     * 请使用include2
     * @return
     */
    @Deprecated
    public IncludeQueryable<TProxy, TEntity> asIncludeQueryable() {
        return new EasyIncludeQueryable<>(castChain());
    }

    /**
     * 创建导航属性路径空对象
     * @return
     */
    @Override
    public TProxy __createNavigatePathEmpty(){
        return createEmpty();
    }

}
