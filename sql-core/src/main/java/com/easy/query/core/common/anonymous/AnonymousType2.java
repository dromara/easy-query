package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType2<TProperty1,TProperty2> extends AnonymousType1<TProperty1> {
    @Column("anonymous_type_p2")
    private TProperty2 p2;

    public TProperty2 getP2() {
        return p2;
    }

    public void setP2(TProperty2 p2) {
        this.p2 = p2;
    }
}
