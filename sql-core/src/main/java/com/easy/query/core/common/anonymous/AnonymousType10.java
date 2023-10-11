package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType10<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6,TProperty7,TProperty8,TProperty9,TProperty10> extends AnonymousType9<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6,TProperty7,TProperty8,TProperty9> {
    @Column("anonymous_type_p10")
    private TProperty10 p10;

    public TProperty10 getP10() {
        return p10;
    }

    public void setP10(TProperty10 p10) {
        this.p10 = p10;
    }
}
