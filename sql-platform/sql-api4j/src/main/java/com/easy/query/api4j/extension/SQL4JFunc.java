package com.easy.query.api4j.extension;

import com.easy.query.api4j.extension.casewhen.CaseWhen4JBuilder;
import com.easy.query.api4j.extension.casewhen.CaseWhen4JBuilder2;
import com.easy.query.api4j.sql.SQLColumnAsSelector;

/**
 * create time 2023/7/3 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQL4JFunc {
    public static <T1,TR> CaseWhen4JBuilder<T1,TR> caseWhenBuilder(SQLColumnAsSelector<T1, TR> sqlColumnAsSelector){
        return new CaseWhen4JBuilder<>(sqlColumnAsSelector);
    }
    public static <T1,T2,TR> CaseWhen4JBuilder2<T1,T2,TR> caseWhenBuilder(SQLColumnAsSelector<T1, TR> sqlColumnAsSelector1,SQLColumnAsSelector<T2, TR> sqlColumnAsSelector2){
        return new CaseWhen4JBuilder2<>(sqlColumnAsSelector1,sqlColumnAsSelector2);
    }
}
