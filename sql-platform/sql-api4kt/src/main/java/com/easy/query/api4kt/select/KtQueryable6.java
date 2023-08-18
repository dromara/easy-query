package com.easy.query.api4kt.select;

import com.easy.query.api4kt.select.extension.queryable6.SQLKtAggregatable6;
import com.easy.query.api4kt.select.extension.queryable6.SQLKtFilterable6;
import com.easy.query.api4kt.select.extension.queryable6.SQLKtGroupable6;
import com.easy.query.api4kt.select.extension.queryable6.SQLKtHavingable6;
import com.easy.query.api4kt.select.extension.queryable6.SQLKtJoinable6;
import com.easy.query.api4kt.select.extension.queryable6.SQLKtOrderable6;
import com.easy.query.api4kt.select.extension.queryable6.SQLKtSelectable6;
import com.easy.query.api4kt.select.extension.queryable6.override.OverrideKtQueryable6;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface KtQueryable6<T1, T2, T3, T4,T5,T6> extends OverrideKtQueryable6<T1, T2, T3, T4,T5,T6>,
        SQLKtAggregatable6<T1, T2, T3, T4,T5,T6>,
        SQLKtFilterable6<T1, T2, T3, T4,T5,T6>,
        SQLKtGroupable6<T1, T2, T3, T4,T5,T6>,
        SQLKtHavingable6<T1, T2, T3, T4,T5,T6>,
        SQLKtOrderable6<T1, T2, T3, T4,T5,T6>,
        SQLKtJoinable6<T1, T2, T3, T4,T5,T6>,
        SQLKtSelectable6<T1, T2, T3, T4,T5,T6> {
}
