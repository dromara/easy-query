package com.easy.query.api4j.func;

import com.easy.query.core.func.SQLFunc;

/**
 * create time 2023/10/12 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class LambdaSQLFuncImpl<T1> implements LambdaSQLFunc<T1> {
    private final SQLFunc sqlFunc;

    public LambdaSQLFuncImpl(SQLFunc sqlFunc){

        this.sqlFunc = sqlFunc;
    }
    @Override
    public SQLFunc getSQLFunc() {
        return sqlFunc;
    }
}
