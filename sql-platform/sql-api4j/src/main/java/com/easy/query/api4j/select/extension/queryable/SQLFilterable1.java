package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable1<T1> {

    default Queryable<T1> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable<T1> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);

    /**
     * 根据id主键查询
     *
     * @param id 主键
     * @return 链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    default Queryable<T1> whereById(Object id) {
        return whereById(true, id);
    }

    /**
     * @param condition
     * @param id
     * @return
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    Queryable<T1> whereById(boolean condition, Object id);

    /**
     * @param ids
     * @param <TProperty>
     * @return
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */

    default <TProperty> Queryable<T1> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    /**
     * 根据主键id集合查询
     *
     * @param condition   是否添加该条件
     * @param ids         主键集合
     * @param <TProperty> 主键类型
     * @return 当前链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */
    <TProperty> Queryable<T1> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default Queryable<T1> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * @param condition
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配,无法获取 {@link SQLWherePredicate}
     */
    Queryable<T1> whereObject(boolean condition, Object object);
}
