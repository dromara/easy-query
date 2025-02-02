package com.easy.query.core.api.dynamic.executor.query;

import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2025/2/2 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConfigureArgument {

    private Object arg;

    public Object getArg() {
        return arg;
    }
    public <T> T getTypeArg() {
        return EasyObjectUtil.typeCastNullable(arg);
    }

    public void setArg(Object arg) {
        this.arg = arg;
    }
}
