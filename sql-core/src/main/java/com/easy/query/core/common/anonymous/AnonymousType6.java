package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType6<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6> extends AnonymousType5<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5> {
    @Column("anonymous_type_p6")
    private TProperty6 p6;

    public TProperty6 getP6() {
        return p6;
    }

    public void setP6(TProperty6 p6) {
        this.p6 = p6;
    }
}
