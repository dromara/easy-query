package com.easy.query.api4kt.select;

import com.easy.query.api4kt.select.extension.queryable7.SQLKtAggregatable7;
import com.easy.query.api4kt.select.extension.queryable7.SQLKtFilterable7;
import com.easy.query.api4kt.select.extension.queryable7.SQLKtGroupable7;
import com.easy.query.api4kt.select.extension.queryable7.SQLKtHavingable7;
import com.easy.query.api4kt.select.extension.queryable7.SQLKtJoinable7;
import com.easy.query.api4kt.select.extension.queryable7.SQLKtOrderable7;
import com.easy.query.api4kt.select.extension.queryable7.SQLKtSelectable7;
import com.easy.query.api4kt.select.extension.queryable7.override.OverrideKtQueryable7;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface KtQueryable7<T1, T2, T3, T4, T5, T6,T7> extends OverrideKtQueryable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLKtAggregatable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLKtFilterable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLKtGroupable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLKtHavingable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLKtOrderable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLKtJoinable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLKtSelectable7<T1, T2, T3, T4, T5, T6,T7> {
}
