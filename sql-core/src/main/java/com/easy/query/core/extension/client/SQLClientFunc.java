package com.easy.query.core.extension.client;

import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/7/3 08:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLClientFunc {
    public static <T1,TR> CaseWhenClientBuilder<T1,TR> caseWhenBuilder(ColumnAsSelector<T1, TR> columnAsSelector){
        return new CaseWhenClientBuilder<>(columnAsSelector);
    }
    public static <T1,T2,TR> CaseWhenClientBuilder2<T1,T2,TR> caseWhenBuilder(ColumnAsSelector<T1, TR> columnAsSelector1,ColumnAsSelector<T2, TR> columnAsSelector2){
        return new CaseWhenClientBuilder2<>(columnAsSelector1,columnAsSelector2);
    }
}
