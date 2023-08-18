package com.easy.query.api4kt.select;

import com.easy.query.api4kt.select.extension.queryable5.SQLKtAggregatable5;
import com.easy.query.api4kt.select.extension.queryable5.SQLKtFilterable5;
import com.easy.query.api4kt.select.extension.queryable5.SQLKtGroupable5;
import com.easy.query.api4kt.select.extension.queryable5.SQLKtHavingable5;
import com.easy.query.api4kt.select.extension.queryable5.SQLKtJoinable5;
import com.easy.query.api4kt.select.extension.queryable5.SQLKtOrderable5;
import com.easy.query.api4kt.select.extension.queryable5.SQLKtSelectable5;
import com.easy.query.api4kt.select.extension.queryable5.override.OverrideKtQueryable5;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface KtQueryable5<T1, T2, T3, T4,T5> extends OverrideKtQueryable5<T1, T2, T3, T4,T5>,
        SQLKtAggregatable5<T1, T2, T3, T4, T5>,
        SQLKtFilterable5<T1, T2, T3, T4, T5>,
        SQLKtGroupable5<T1, T2, T3, T4, T5>,
        SQLKtHavingable5<T1, T2, T3, T4, T5>,
        SQLKtOrderable5<T1, T2, T3, T4, T5>,
        SQLKtJoinable5<T1, T2, T3, T4, T5>,
        SQLKtSelectable5<T1, T2, T3, T4, T5> {
}
