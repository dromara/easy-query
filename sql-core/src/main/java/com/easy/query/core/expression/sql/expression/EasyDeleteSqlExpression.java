package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;

/**
 * create time 2023/4/23 07:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyDeleteSqlExpression extends EasyEntityPredicateSqlExpression{
    EasyQueryRuntimeContext getRuntimeContext();

    @Override
    EasyDeleteSqlExpression cloneSqlExpression();
}
