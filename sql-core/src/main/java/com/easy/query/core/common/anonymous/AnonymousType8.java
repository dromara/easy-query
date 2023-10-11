package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType8<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6,TProperty7,TProperty8> extends AnonymousType7<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6,TProperty7> {
    @Column("anonymous_type_p8")
    private TProperty8 p8;

    public TProperty8 getP8() {
        return p8;
    }

    public void setP8(TProperty8 p8) {
        this.p8 = p8;
    }
}
