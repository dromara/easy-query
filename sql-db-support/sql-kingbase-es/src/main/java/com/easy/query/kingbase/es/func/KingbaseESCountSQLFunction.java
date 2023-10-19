package com.easy.query.kingbase.es.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.CountSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 08:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESCountSQLFunction extends CountSQLFunction {
    public KingbaseESCountSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new KingbaseESNullDefaultSQLFunction(columnExpressions);
    }
}
