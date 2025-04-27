package com.easy.query.core.func.def.impl;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.column.ColumnFunctionExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2024/3/11 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractLikeSQLFunction extends AbstractExpressionSQLFunction {

    protected ColumnFuncValueExpression getColumnFuncValueExpression(ColumnExpression columnExpression) {
        if (columnExpression instanceof ColumnFuncValueExpression) {
            return (ColumnFuncValueExpression) columnExpression;
        }

        if (columnExpression instanceof ColumnFunctionExpression) {
            ColumnFunctionExpression columnFunctionExpression = (ColumnFunctionExpression) columnExpression;
            SQLFunction sqlFunction = columnFunctionExpression.getSQLFunction();
            if (sqlFunction instanceof ConstSQLFunction) {
                ConstSQLFunction constSQLFunction = (ConstSQLFunction) sqlFunction;
                if (EasyCollectionUtil.isSingle(constSQLFunction.getColumnExpressions())) {
                    ColumnExpression constSQLExpressionFirst = EasyCollectionUtil.first(constSQLFunction.getColumnExpressions());
                    if (constSQLExpressionFirst instanceof ColumnFuncValueExpression) {
                        return (ColumnFuncValueExpression) constSQLExpressionFirst;
                    }
                }
            }
        }
        return null;
    }
}
