package com.easy.query.api4kt.select;

import com.easy.query.api4kt.select.extension.queryable10.SQLKtAggregatable10;
import com.easy.query.api4kt.select.extension.queryable10.SQLKtFilterable10;
import com.easy.query.api4kt.select.extension.queryable10.SQLKtGroupable10;
import com.easy.query.api4kt.select.extension.queryable10.SQLKtHavingable10;
import com.easy.query.api4kt.select.extension.queryable10.SQLKtOrderable10;
import com.easy.query.api4kt.select.extension.queryable10.SQLKtSelectable10;
import com.easy.query.api4kt.select.extension.queryable10.override.OverrideKtQueryable10;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface KtQueryable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10> extends OverrideKtQueryable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLKtAggregatable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLKtFilterable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLKtGroupable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLKtHavingable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLKtOrderable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLKtSelectable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10> {
}
