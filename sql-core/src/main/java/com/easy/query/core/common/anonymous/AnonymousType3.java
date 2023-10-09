package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType3<TProperty1,TProperty2,TProperty3> extends AnonymousType2<TProperty1,TProperty2> {
    @Column("anonymous_type_p3")
    private TProperty3 p3;

    public TProperty3 getP3() {
        return p3;
    }

    public void setP3(TProperty3 p3) {
        this.p3 = p3;
    }
}
