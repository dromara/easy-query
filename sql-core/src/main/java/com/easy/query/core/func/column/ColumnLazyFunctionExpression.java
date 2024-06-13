package com.easy.query.core.func.column;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;

import java.util.function.Function;

/**
 * create time 2023/10/18 13:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnLazyFunctionExpression extends ColumnExpression{
    TableAvailable getTableOrNull();
    Function<SQLFunc, SQLFunction> getSQLFunctionCreator();
}
