package com.easy.query.core.proxy.func;

import com.easy.query.core.func.SQLFunc;

/**
 * create time 2023/10/12 21:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxySQLFuncImpl implements ProxySQLFunc {
    private final SQLFunc sqlFunc;

    public ProxySQLFuncImpl(SQLFunc sqlFunc){

        this.sqlFunc = sqlFunc;
    }
    @Override
    public SQLFunc getSQLFunc() {
        return sqlFunc;
    }
}
