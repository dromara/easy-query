package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.expression.segment.SqlSegment;

import java.util.List;


/**
 * create time 2023/4/22 21:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasySqlExpression extends SqlSegment {
    EasySqlExpression cloneSqlExpression();
}
