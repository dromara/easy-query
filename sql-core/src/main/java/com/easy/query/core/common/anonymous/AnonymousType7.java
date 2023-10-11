package com.easy.query.core.common.anonymous;

import com.easy.query.core.annotation.Column;

/**
 * create time 2023/10/9 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousType7<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6,TProperty7> extends AnonymousType6<TProperty1,TProperty2,TProperty3,TProperty4,TProperty5,TProperty6> {
    @Column("anonymous_type_p7")
    private TProperty7 p7;

    public TProperty7 getP7() {
        return p7;
    }

    public void setP7(TProperty7 p7) {
        this.p7 = p7;
    }
}
