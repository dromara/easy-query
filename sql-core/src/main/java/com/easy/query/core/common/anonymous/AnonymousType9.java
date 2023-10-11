package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType9<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6,TProperty7,TProperty8,TProperty9> extends AnonymousType8<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6,TProperty7,TProperty8> {
    @Column("anonymous_type_p9")
    private TProperty9 p9;

    public TProperty9 getP9() {
        return p9;
    }

    public void setP9(TProperty9 p9) {
        this.p9 = p9;
    }
}
