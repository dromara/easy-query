package com.easy.query.api.proxy.func;

import com.easy.query.core.func.SQLFunc;

/**
 * create time 2023/10/12 21:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLProxyFunc implements SQLProxyFunc{
    private final SQLFunc sqlFunc;

    public DefaultSQLProxyFunc(SQLFunc sqlFunc){

        this.sqlFunc = sqlFunc;
    }
    @Override
    public SQLFunc getSQLFunc() {
        return sqlFunc;
    }
}
