package com.easy.query.core.expression.parser.core;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.IndexAvailable;

/**
 *
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 * @author xuejiaming
 */
public interface SqlColumnResultSelector<T1,TMember> extends IndexAvailable {
     void column(Property<T1,TMember> column);
}
