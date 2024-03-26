package com.easy.query.core.proxy.grouping.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnObjectFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/28 14:57
 * 抽象的group代理对象
 *
 * @author xuejiaming
 */
public abstract class AbstractGrouping1Proxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TSourceProxy> extends AbstractProxyEntity<TProxy, TEntity> implements SQLGroupByExpression {

    private final TSourceProxy tSourceProxy;

    public AbstractGrouping1Proxy(TSourceProxy tSourceProxy) {
        this.tSourceProxy = tSourceProxy;
    }

    /**
     * 请使用{@link #groupTable()}
     * 当仅单表是group就是当前表
     * 如果是多表下比如join下那么groups就是MergeTuple2-10最多10个如有需要可以提交issue或者自行扩展
     *
     * @return
     */
    @Deprecated
    public TSourceProxy group() {
        return tSourceProxy;
    }

    /**
     * 当仅单表是group就是当前表
     * 如果是多表下比如join下那么groups就是MergeTuple2-10最多10个如有需要可以提交issue或者自行扩展
     * @return
     */
    public TSourceProxy groupTable() {
        return tSourceProxy;
    }

    /**
     * 请使用{@link #expression()}或者{@link Expression#count()}
     * COUNT(*)
     *
     * @return 返回类型为Long
     */
    public ColumnFunctionComparableNumberChainExpression<Long> count() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(getEntitySQLContext(), null, null, f -> {
            return f.count(c -> {
            });
        }, Long.class);
    }

//    public <TProperty> ColumnFunctionComparableNumberChainExpression<Long> count(ColumnObjectFunctionAvailable<TProperty, ?> column) {
//        return column.count();
//    }
    public <TProperty> ColumnFunctionComparableNumberChainExpression<Long> count(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.count();
        }
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, Long.class);
    }

    /**
     * 请使用{@link #expression()}或者{@link Expression#intCount()}
     * COUNT(*)
     *
     * @return 返回类型为Integer
     */
    public ColumnFunctionComparableNumberChainExpression<Integer> intCount() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(getEntitySQLContext(), null, null, f -> {
            return f.count(c -> {
            });
        }, Integer.class);
    }

//    public <TProperty> ColumnFunctionComparableNumberChainExpression<Integer> intCount(ColumnObjectFunctionAvailable<TProperty, ?> column) {
//        return column.intCount();
//    }
    public <TProperty> ColumnFunctionComparableNumberChainExpression<Integer> intCount(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.intCount();
        }
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, Integer.class);
    }

    /**
     * 请使用 id().max()
     * @param column
     * @return
     * @param <TProperty>
     * @param <TChain>
     */
    @Deprecated
    public <TProperty, TChain extends PropTypeColumn<TProperty>> PropTypeColumn<TProperty> max(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnObjectFunctionAvailable) {
            ColumnObjectFunctionAvailable<TProperty, TChain> column1 = (ColumnObjectFunctionAvailable<TProperty, TChain>) column;
            return column1.max();
        }
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.max(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, column.getPropertyType());
    }
//    public <TProperty, TChain extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> TChain max(ColumnObjectFunctionAvailable<TProperty, TChain> column) {
//        return column.max();
//    }

//    public <TProperty, TChain extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> TChain min(ColumnObjectFunctionAvailable<TProperty, TChain> column) {
//        return column.min();
//    }
//    public <TProperty extends Number> ColumnFunctionComparableNumberChainExpression<TProperty> sum(ColumnNumberFunctionAvailable<TProperty> column) {
//        return column.sum();
//    }

    /**
     * 请使用 id().min()
     * @param column
     * @return
     * @param <TProperty>
     * @param <TChain>
     */
    @Deprecated
    public <TProperty, TChain extends PropTypeColumn<TProperty>> PropTypeColumn<TProperty> min(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnObjectFunctionAvailable) {
            ColumnObjectFunctionAvailable<TProperty, TChain> column1 = (ColumnObjectFunctionAvailable<TProperty, TChain>) column;
            return column1.min();
        }
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.min(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, column.getPropertyType());
    }

    /**
     * 请使用 age().sum()
     * @param column
     * @return
     * @param <TProperty>
     */
    @Deprecated
    public <TProperty extends Number> ColumnFunctionComparableNumberChainExpression<TProperty> sum(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.sum();
        }
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, column.getPropertyType());
    }


    /**
     * 请使用 age().sumBigDecimal()
     * @param column
     * @return
     * @param <TProperty>
     */
    @Deprecated
    public <TProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.sumBigDecimal();
        }
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, BigDecimal.class);
    }
//    public <TProperty> ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(ColumnNumberFunctionAvailable<TProperty> column) {
//        return column.sumBigDecimal();
//    }

//    public <TProperty> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(ColumnNumberFunctionAvailable<TProperty> column) {
//        return column.avg();
//    }

    /**
     * 请使用 age().avg()
     * @param column
     * @return
     * @param <TProperty>
     */
    @Deprecated
    public <TProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.avg();
        }
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.avg(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, BigDecimal.class);
    }

    /**
     * 请使用 age().join(",")
     * @param column
     * @param delimiter
     * @return
     * @param <TProperty>
     */
    @Deprecated
    public <TProperty> ColumnFunctionComparableStringChainExpression<String> join(ColumnStringFunctionAvailable<TProperty> column, String delimiter) {
        return column.join(delimiter);
    }

    /**
     * 请使用 age().join(",")
     * @param column
     * @param delimiter
     * @return
     * @param <TProperty>
     */
    @Deprecated
    public <TProperty> ColumnFunctionComparableStringChainExpression<String> join(PropTypeColumn<TProperty> column, String delimiter) {
        if (column instanceof ColumnStringFunctionAvailable) {
            ColumnStringFunctionAvailable<TProperty> funcColumn = (ColumnStringFunctionAvailable<TProperty>) column;
            return funcColumn.join(delimiter);
        }
        return new ColumnFunctionComparableStringChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.join(x -> {
                x.value(delimiter);
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, String.class);
    }

    protected <TKey extends PropTypeColumn<TKey1>, TKey1> void acceptGroupSelector(TKey key, GroupSelector s) {

        if (key instanceof DSLSQLFunctionAvailable) {
            Function<SQLFunc, SQLFunction> funcCreate = ((DSLSQLFunctionAvailable) key).func();
            SQLFunc fx = s.getRuntimeContext().fx();
            SQLFunction sqlFunction = funcCreate.apply(fx);
            s.columnFunc(key.getTable(), sqlFunction);
        } else {
            key.accept(s);
//            if(key instanceof SQLNativeDraft){
//            }else{
//                s.column(key.getTable(), key.getValue());
//            }
        }
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }


//    public <TProperty, TChain extends PropTypeColumn<TProperty>> TChain min(PropTypeColumn<TProperty> column) {
//        if(column instanceof ColumnObjectFunctionAvailable){
//            ColumnObjectFunctionAvailable<TProperty,TChain> column1 = (ColumnObjectFunctionAvailable<TProperty,TChain>) column;
//            return column1.min();
//        }
//        return EasyObjectUtil.typeCastNullable(new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
//            return fx.min(x -> {
//                PropTypeColumn.columnFuncSelector(x, column);
//            });
//        }, column.getPropertyType()));
//    }
//    public <TProperty extends Number> ColumnFunctionComparableNumberChainExpression<TProperty> sum(PropTypeColumn<TProperty> column) {
//        if(column instanceof ColumnNumberFunctionAvailable){
//            ColumnNumberFunctionAvailable<TProperty> column1 = (ColumnNumberFunctionAvailable<TProperty>) column;
//            return column1.sum();
//        }
//        return new ColumnFunctionComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
//            return fx.sum(x->{
//                PropTypeColumn.columnFuncSelector(x,column);
//            });
//        }, column.getPropertyType());
//    }
}