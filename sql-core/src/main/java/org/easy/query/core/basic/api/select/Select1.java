package org.easy.query.core.basic.api.select;

import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: Select11.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:00
 * @Created by xuejiaming
 */
public interface Select1<T1> extends Select0<T1, Select1<T1>> {

    <T2> Select2<T1, T2> leftJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);

    <T2> Select2<T1, T2> innerJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);
}
