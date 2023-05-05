package com.easy.query.core.expression.parser.core;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.IndexAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 *
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 * @author xuejiaming
 */
public interface SqlColumnResultSelector<T1,TMember> {
     TableAvailable getTable();
     void column(Property<T1,TMember> column);
}
