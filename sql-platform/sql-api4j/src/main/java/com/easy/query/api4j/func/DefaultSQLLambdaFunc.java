package com.easy.query.api4j.func;

import com.easy.query.core.func.SQLFunc;

/**
 * create time 2023/10/12 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLLambdaFunc implements SQLLambdaFunc{
    private final SQLFunc sqlFunc;

    public DefaultSQLLambdaFunc(SQLFunc sqlFunc){

        this.sqlFunc = sqlFunc;
    }
    @Override
    public SQLFunc getSQLFunc() {
        return sqlFunc;
    }
}
