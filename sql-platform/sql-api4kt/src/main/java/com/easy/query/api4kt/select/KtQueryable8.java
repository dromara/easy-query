package com.easy.query.api4kt.select;

import com.easy.query.api4kt.select.extension.queryable8.SQLKtAggregatable8;
import com.easy.query.api4kt.select.extension.queryable8.SQLKtFilterable8;
import com.easy.query.api4kt.select.extension.queryable8.SQLKtGroupable8;
import com.easy.query.api4kt.select.extension.queryable8.SQLKtHavingable8;
import com.easy.query.api4kt.select.extension.queryable8.SQLKtJoinable8;
import com.easy.query.api4kt.select.extension.queryable8.SQLKtOrderable8;
import com.easy.query.api4kt.select.extension.queryable8.SQLKtSelectable8;
import com.easy.query.api4kt.select.extension.queryable8.override.OverrideKtQueryable8;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface KtQueryable8<T1, T2, T3, T4, T5, T6,T7,T8> extends OverrideKtQueryable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLKtAggregatable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLKtFilterable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLKtGroupable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLKtHavingable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLKtOrderable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLKtJoinable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLKtSelectable8<T1, T2, T3, T4, T5, T6,T7,T8> {
}
