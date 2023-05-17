package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.expression.segment.SQLSegment;


/**
 * create time 2023/4/22 21:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLExpression extends SQLSegment {
    SQLExpression cloneSQLExpression();
}
