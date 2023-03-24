package com.easy.query.core.expression.parser.abstraction;

import com.easy.query.core.expression.parser.abstraction.internal.ColumnAsSelector;

/**
 *
 * @FileName: SqlSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:07
 * @author xuejiaming
 */
public interface SqlColumnAsSelector<T1,TR> extends ColumnAsSelector<T1,TR, SqlColumnAsSelector<T1,TR>> {
}
