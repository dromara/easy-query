package com.easy.query.api.proxy.extension.window;

/**
 * create time 2025/10/11 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class FirstValueOffset<TProperty> extends Offset<TProperty>{
    public FirstValueOffset() {
        super(-1);
    }
    @Override
    public int getOffset() {
        return -1;
    }

    @Override
    public String getSQLFunction() {
        return "FIRST_VALUE";
    }
}
