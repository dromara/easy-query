package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/3 14:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLSQLFunctionAvailable extends SQLTableOwner, EntitySQLContextAvailable {
    Function<SQLFunc, SQLFunction> func();
}
