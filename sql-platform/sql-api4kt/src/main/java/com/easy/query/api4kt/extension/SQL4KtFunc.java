package com.easy.query.api4kt.extension;

import com.easy.query.api4kt.extension.casewhen.CaseWhen4KtBuilder;
import com.easy.query.api4kt.extension.casewhen.CaseWhen4KtBuilder2;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;

/**
 * create time 2023/7/3 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQL4KtFunc {
    public static <T1,TR> CaseWhen4KtBuilder<T1,TR> caseWhenBuilder(SQLKtColumnAsSelector<T1, TR> sqlColumnAsSelector){
        return new CaseWhen4KtBuilder<>(sqlColumnAsSelector);
    }
    public static <T1,T2,TR> CaseWhen4KtBuilder2<T1,T2,TR> caseWhenBuilder(SQLKtColumnAsSelector<T1, TR> sqlColumnAsSelector1,SQLKtColumnAsSelector<T2, TR> sqlColumnAsSelector2){
        return new CaseWhen4KtBuilder2<>(sqlColumnAsSelector1,sqlColumnAsSelector2);
    }
}
