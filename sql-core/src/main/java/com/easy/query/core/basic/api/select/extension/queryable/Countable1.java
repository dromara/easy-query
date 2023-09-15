package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * create time 2023/9/14 22:28
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Countable1<T1> {

    ClientQueryable<Long> selectCount();
//    ClientQueryable<Long> selectCount(SQLExpression1<ColumnResultSelector<T1>> countColumnFunc);
    <TNumber extends Number>ClientQueryable<TNumber> selectCount(Class<TNumber> numberClass);

}
