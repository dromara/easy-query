package com.easy.query.api.proxy.extension.window;

/**
 * create time 2025/10/11 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class LastValueOffset<TProperty> extends Offset<TProperty>{
    public LastValueOffset() {
        super(-1);
    }

    @Override
    public int getOffset() {
        return -1;
    }

    @Override
    public String getSQLFunction() {
        return "LAST_VALUE";
    }
}
