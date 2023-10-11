package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType4<TProperty1,TProperty2,TProperty3,TProperty4> extends AnonymousType3<TProperty1,TProperty2,TProperty3> {
    @Column("anonymous_type_p4")
    private TProperty4 p4;

    public TProperty4 getP4() {
        return p4;
    }

    public void setP4(TProperty4 p4) {
        this.p4 = p4;
    }
}
