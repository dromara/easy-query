package com.easy.query.api4kt.sql.core.available;

import com.easy.query.api4kt.func.KtLambdaSQLFuncImpl;
import com.easy.query.api4kt.func.KtLambdaSQLFunc;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;

/**
 * create time 2023/10/13 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtLambdaFuncAvailable<T1> extends RuntimeContextAvailable {
    default KtLambdaSQLFunc<T1> sqlFunc(){
        return new KtLambdaSQLFuncImpl<>(getRuntimeContext().sqlFunc());
    }
}