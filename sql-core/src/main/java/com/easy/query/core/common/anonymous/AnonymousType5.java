package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType5<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5> extends AnonymousType4<TProperty1,TProperty2,TProperty3,TProperty4> {
    @Column("anonymous_type_p5")
    private TProperty5 p5;

    public TProperty5 getP5() {
        return p5;
    }

    public void setP5(TProperty5 p5) {
        this.p5 = p5;
    }
}
