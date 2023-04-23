package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;

/**
 * @FileName: SqlSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:07
 * @author xuejiaming
 */
public interface SqlSegment {
//    default String toSql(){
//        return toSql(null);
//    }
    String toSql(SqlParameterCollector sqlParameterCollector);
}
