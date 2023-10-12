package com.easy.query.api4kt.func;

import com.easy.query.core.func.SQLFunc;

/**
 * create time 2023/10/12 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLKtLambdaFunc implements SQLKtLambdaFunc {
    private final SQLFunc sqlFunc;

    public DefaultSQLKtLambdaFunc(SQLFunc sqlFunc){

        this.sqlFunc = sqlFunc;
    }
    @Override
    public SQLFunc getSQLFunc() {
        return sqlFunc;
    }
}
