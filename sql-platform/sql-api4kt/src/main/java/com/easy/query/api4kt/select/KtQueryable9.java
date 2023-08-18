package com.easy.query.api4kt.select;

import com.easy.query.api4kt.select.extension.queryable9.SQLKtAggregatable9;
import com.easy.query.api4kt.select.extension.queryable9.SQLKtFilterable9;
import com.easy.query.api4kt.select.extension.queryable9.SQLKtGroupable9;
import com.easy.query.api4kt.select.extension.queryable9.SQLKtHavingable9;
import com.easy.query.api4kt.select.extension.queryable9.SQLKtJoinable9;
import com.easy.query.api4kt.select.extension.queryable9.SQLKtOrderable9;
import com.easy.query.api4kt.select.extension.queryable9.SQLKtSelectable9;
import com.easy.query.api4kt.select.extension.queryable9.override.OverrideKtQueryable9;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface KtQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> extends OverrideKtQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLKtAggregatable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLKtFilterable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLKtGroupable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLKtHavingable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLKtOrderable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLKtJoinable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLKtSelectable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> {
}
