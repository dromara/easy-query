package com.easy.query.core.proxy.set;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLColumnDecrementImpl;
import com.easy.query.core.proxy.impl.SQLColumnIncludeColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnIncrementImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetNativeSQLImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetPropColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetSubQueryImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/8 10:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLColumnSet<TProxy, TProperty> extends PropTypeColumn<TProperty>, ChainCast<TProxy>, TablePropColumn, EntitySQLContextAvailable {
    default TProxy set(TProperty val) {
        return set(true, val);
    }

    default TProxy set(boolean condition, TProperty val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLColumnSetValueImpl(getTable(), getValue(), val));
        }
        return castChain();
    }

    default TProxy setNull() {
        return setNull(true);
    }

    default TProxy setNull(boolean condition) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLColumnSetValueImpl(getTable(), getValue(), null));
        }
        return castChain();
    }
//
//    default void set(SQLColumn<?, TProperty> column) {
//        set(true, column);
//    }
//
//    default void set(boolean condition, SQLColumn<?, TProperty> column) {
//        if (condition) {
//            getCurrentEntitySQLContext().accept(new SQLColumnSetColumnImpl(getTable(), getValue(), column));
//        }
//    }
//
//    /**
//     * 支持function函数
//     * @param val
//     * @param <TResult>
//     */
//    default <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> void set(TResult val) {
//        set(true, val);
//    }
//
//    default <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> void set(boolean condition, TResult val) {
//        if (condition) {
//            getCurrentEntitySQLContext().accept(new SQLColumnSetSQLFunctionValueImpl(getTable(), getValue(), val));
//        }
//    }

    /**
     * 支持function函数
     *
     * <blockquote><pre>
     * {@code
     *   .select(o->new ResultProxy().adapter(r->{
     *      //返回结果resultProperty设置为一个表达式返回boolean类型 resultProperty= (owner == userId)
     *      r.resultProperty().set(o.owner().equalsWith(webCurrentUser.getUserId()))
     *   })
     *   .select(o->new ResultProxy().adapter(r->{
     *      //返回结果resultProperty设置为一个表达式返回boolean类型 resultProperty= (owner == userId)
     *      r.resultProperty().set(o.isTop())
     *   })
     *   .select(o->new ResultProxy().adapter(r->{
     *      //返回结果resultProperty设置为一个表达式 resultProperty= title.subString(1,2)
     *      r.resultProperty().set(o.title().subString(1,2))
     *      //返回结果resultProperty设置为一个表达式resultProperty= (title==null?"123":title)
     *      r.resultProperty().set(o.title().nullOrDefault("123"))
     *   })
     * }
     * </pre></blockquote>
     *
     * @param val
     * @param <TResult>
     */
    default <TResult extends PropTypeColumn<TProperty>> TProxy set(TResult val) {
        return set(true, val);
    }

    default <TResult extends PropTypeColumn<TProperty>> TProxy set(boolean condition, TResult val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLColumnSetPropColumnImpl(getTable(), getValue(), val));
        }
        return castChain();
    }

    default TProxy setSQL(String sqlSegment) {
        return setSQL(sqlSegment, c -> {
        });
    }

    default TProxy setSQL(String sqlSegment, SQLActionExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return setSQL(true, sqlSegment, contextConsume);
    }


    default TProxy setSQL(boolean condition, String sqlSegment, SQLActionExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLColumnSetNativeSQLImpl(getTable(), getValue(), sqlSegment, contextConsume));
        }
        return castChain();
    }
//
//    default TProxy setConverter(Consumer<DSLColumnSet<TProxy,TProperty>> setConsumer) {
//        setConsumer.accept(this);
//        return castChain();
//    }

    /**
     * 设置子查询
     * o.userCount().set(subQuery)
     * select (select count(*) from table) as userCount
     *
     * @param subQuery 子查询
     */
    default TProxy setSubQuery(Query<TProperty> subQuery) {
        return setSubQuery(true, subQuery);
    }

    /**
     * 设置子查询
     * o.userCount().set(subQuery)
     * select (select count(*) from table) as userCount
     *
     * @param condition 是否使用这个查询赋值
     * @param subQuery  子查询
     */
    default TProxy setSubQuery(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLColumnSetSubQueryImpl(getTable(), getValue(), subQuery));
        }
        return castChain();
    }

    /**
     * 查询表达式
     * x.title().sqlSelectExpression(o.content())
     * select content as title from table
     *
     * @param sqlSelectExpression 查询表达式
     */
    default TProxy setExpression(SQLSelectExpression sqlSelectExpression) {
        return setExpression(true, sqlSelectExpression);
    }


    /**
     * 查询表达式
     * x.title().sqlSelectExpression(o.content())
     * select content as title from table
     *
     * @param condition           是否追加这个select
     * @param sqlSelectExpression 查询表达式
     */
    default TProxy setExpression(boolean condition, SQLSelectExpression sqlSelectExpression) {
        if (condition) {
            getCurrentEntitySQLContext().accept(sqlSelectExpression.as(getValue()));
        }
        return castChain();
    }

    /**
     * 查询设置导航属性对一 对多
     * <blockquote><pre>
     * {@code
     * .select(o->new SchoolStudentVOProxy().adapter(r->{
     *                             r.schoolClass().setNavigate(o.schoolClass());
     *                         }))
     *                    }
     * </pre></blockquote>
     *
     * @param column                 对一或者对多的导航属性
     * @param <TSourcePropertyProxy> 对一或者对一类型代理
     * @param <TSourceProperty>      对一或者对一类型
     */
    @Deprecated
    default <TSourcePropertyProxy extends ProxyEntity<TSourcePropertyProxy, TSourceProperty>, TSourceProperty extends ProxyEntityAvailable<TSourceProperty, TSourcePropertyProxy>>
    TProxy setNavigate(SQLColumn<?, TSourceProperty> column) {
        return setNavigate(column, null);
    }

    /**
     * 查询设置导航属性对一 对多
     *
     * <blockquote><pre>
     * {@code
     * .select(o->new SchoolStudentVOProxy().adapter(r->{
     *                             r.schoolClass().setNavigate(o.schoolClass(),t->new SchoolClassVOProxy());
     *                         }))
     *                    }
     * </pre></blockquote>
     *
     * @param column                   对一或者对多的导航属性
     * @param navigateSelectExpression 映射的结果返回方法
     * @param <TPropertyProxy>         返回映射的对多对一类型代理
     * @param <TSourcePropertyProxy>   对一或者对一类型代理
     * @param <TSourceProperty>        对一或者对一类型
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TSourcePropertyProxy extends ProxyEntity<TSourcePropertyProxy, TSourceProperty>, TSourceProperty extends ProxyEntityAvailable<TSourceProperty, TSourcePropertyProxy>>
    TProxy setNavigate(SQLColumn<?, TSourceProperty> column
            , SQLFuncExpression1<TSourcePropertyProxy, TPropertyProxy> navigateSelectExpression) {
        Class<TSourceProperty> propertyType = EasyObjectUtil.typeCastNullable(column.getPropertyType());
        TSourcePropertyProxy tSourcePropertyProxy = EntityQueryProxyManager.create(propertyType);
        getCurrentEntitySQLContext().accept(new SQLColumnIncludeColumnImpl<>(column.getTable(), column.getValue(), getValue(), tSourcePropertyProxy, navigateSelectExpression));
        return castChain();
    }
//
//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy,TProperty>> void setNavigate(TPropertyProxy columnProxy) {
//        setNavigate(true, columnProxy);
//    }
//
//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy,TProperty>> void setNavigate(boolean condition, TPropertyProxy columnProxy) {
//        if (condition) {
//            getCurrentEntitySQLContext().accept(new SQLColumnSetColumnImpl(getTable(), getValue(), column));
//        }
//    }

    /**
     * 自增 a+1
     * update set age=age+1
     */
    default TProxy increment() {
        return increment(true);
    }

    /**
     * 自增 a+1
     * update set age=age+1
     *
     * @param condition 是否生效
     */
    default TProxy increment(boolean condition) {
        if (condition) {
            increment(1);
        }
        return castChain();
    }

    /**
     * 自减 a-1
     * update set age=age-1
     */
    default TProxy decrement() {
        return decrement(true);
    }

    /**
     * 自减 a-1
     * update set age=age-1
     *
     * @param condition 是否生效
     */
    default TProxy decrement(boolean condition) {
        if (condition) {
            decrement(1);
        }
        return castChain();
    }


    /**
     * 自增 a+?
     * update set age=age+?
     *
     * @param val
     * @param <T>
     */
    default <T extends Number> TProxy increment(Number val) {
        return increment(true, val);
    }

    /**
     * 自增 a+?
     * update set age=age+?
     *
     * @param condition
     * @param val
     * @param <T>
     */
    default <T extends Number> TProxy increment(boolean condition, Number val) {
        if (condition) {
//            getCurrentEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
//                x.setIncrementNumber(true, getTable(), getValue(), val);
//            }));
            getCurrentEntitySQLContext().accept(new SQLColumnIncrementImpl(getTable(), getValue(), val));
        }
        return castChain();
    }

    /**
     * 自减 a-?
     * update set age=age-?
     *
     * @param val
     * @param <T>
     */
    default <T extends Number> TProxy decrement(Number val) {
        return decrement(true, val);
    }

    /**
     * 自减 a-?
     * update set age=age-?
     *
     * @param condition
     * @param val
     * @param <T>
     */
    default <T extends Number> TProxy decrement(boolean condition, Number val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLColumnDecrementImpl(getTable(), getValue(), val));
        }
        return castChain();
    }
}
