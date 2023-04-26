package com.easy.query.core.expression.sql.expression;

import java.util.List;

/**
 * create time 2023/4/23 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyEntitySqlExpression extends EasySqlExpression {
    List<EasyTableSqlExpression> getTables();

    @Override
    EasyEntitySqlExpression cloneSqlExpression();
}
