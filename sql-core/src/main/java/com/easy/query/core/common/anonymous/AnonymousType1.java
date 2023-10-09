package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType1<TProperty1> implements AnonymousType {
    @Column("anonymous_type_p1")
    private TProperty1 p1;

    public TProperty1 getP1() {
        return p1;
    }

    public void setP1(TProperty1 p1) {
        this.p1 = p1;
    }
}
