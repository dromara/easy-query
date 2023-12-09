package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;

import java.util.function.Function;

/**
 * create time 2023/12/6 11:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFunctionExpressionUtil {

    public static <T extends SQLNative<T>& RuntimeContextAvailable> void accept(T sqlNative, TableAvailable table, Function<SQLFunc, SQLFunction> sqlFunc){

        SQLFunction sqlFunction = sqlFunc.apply(sqlNative.getRuntimeContext().fx());
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlNative.sqlNativeSegment(sqlSegment,context->{
            sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table,context));
        });
    }
    public static <T extends SQLNative<T>& RuntimeContextAvailable> void accept(T sqlNative, TableAvailable table, Function<SQLFunc, SQLFunction> sqlFunc,boolean asc){

        SQLFunction sqlFunction = sqlFunc.apply(sqlNative.getRuntimeContext().fx());
        String sqlSegment = sqlFunction.sqlSegment(table)+(asc?" ASC":" DESC");
        sqlNative.sqlNativeSegment(sqlSegment,context->{
            sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table,context));
        });
    }
}
