package org.easy.query.core.basic.expression.parser.abstraction;

import org.easy.query.core.basic.expression.parser.abstraction.internal.WherePredicate;

/**
 *
 * @FileName: SqlPredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 15:38
 * @Created by xuejiaming
 */
public interface SqlPredicate<T1> extends WherePredicate<T1, SqlPredicate<T1>> {
}
