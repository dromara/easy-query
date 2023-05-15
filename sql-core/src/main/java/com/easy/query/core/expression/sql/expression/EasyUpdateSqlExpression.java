package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;

/**
 * create time 2023/4/22 22:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyUpdateSqlExpression extends EasyEntityPredicateSqlExpression{
    SqlBuilderSegment getSetColumns();

    @Override
    EasyUpdateSqlExpression cloneSqlExpression();
}
