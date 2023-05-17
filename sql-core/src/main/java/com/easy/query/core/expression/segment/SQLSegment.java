package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;

/**
 * @Description: 文件说明
 * @Date: 2023/2/14 23:07
 * @author xuejiaming
 */
public interface SQLSegment {
    String toSQL(SQLParameterCollector sqlParameterCollector);
}
