package com.easy.query.api.proxy.extension.window;

/**
 * create time 2025/10/11 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class NthValueOffset<TProperty> extends Offset<TProperty>{
    public NthValueOffset(int offset) {
        super(offset);
    }

    @Override
    public String getSQLFunction() {
        return "NTH_VALUE";
    }
}
