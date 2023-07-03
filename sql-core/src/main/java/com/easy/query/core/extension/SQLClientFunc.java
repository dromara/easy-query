package com.easy.query.core.extension;

import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/7/3 08:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLClientFunc {
    public static <T1,TR> CaseWhenClientBuilder<T1,TR> caseWhenBuilder(ColumnAsSelector<T1, TR> sqlColumnAsSelector){
        return new CaseWhenClientBuilder<>(sqlColumnAsSelector);
    }
}
