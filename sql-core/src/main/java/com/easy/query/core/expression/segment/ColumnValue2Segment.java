package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

/**
 * create time 2024/12/5 13:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnValue2Segment extends Column2Segment {
    SQLParameter getSQLParameter();
}
