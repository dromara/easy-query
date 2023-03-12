package com.easy.query.core.expression.parser.abstraction;

import com.easy.query.core.expression.parser.abstraction.internal.AggregatePredicate;

/**
 * @FileName: SqlAggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:40
 * @Created by xuejiaming
 */
public interface SqlAggregatePredicate<T1> extends AggregatePredicate<T1,SqlAggregatePredicate<T1>> {
}
