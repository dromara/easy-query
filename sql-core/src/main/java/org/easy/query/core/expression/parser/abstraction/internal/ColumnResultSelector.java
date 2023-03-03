package org.easy.query.core.expression.parser.abstraction.internal;

import org.easy.query.core.expression.lambda.Property;

/**
 *
 * @FileName: SqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 * @Created by xuejiaming
 */
public interface ColumnResultSelector<T1,TMember> extends IndexAware {
     void column(Property<T1,TMember> column);
}
