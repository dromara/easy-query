package com.easy.query.api4j.sql.core.available;

import com.easy.query.api4j.func.LambdaSQLFuncImpl;
import com.easy.query.api4j.func.LambdaSQLFunc;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;

/**
 * create time 2023/10/13 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LambdaSQLFuncAvailable<T1> extends RuntimeContextAvailable {
    default LambdaSQLFunc<T1> sqlFunc(){
        return new LambdaSQLFuncImpl<>(getRuntimeContext().sqlFunc());
    }
}