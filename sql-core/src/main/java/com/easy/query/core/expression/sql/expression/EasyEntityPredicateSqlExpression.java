package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * create time 2023/4/23 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyEntityPredicateSqlExpression extends EasyEntitySqlExpression{
    PredicateSegment getWhere();
}
