package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.proxy.impl.SQLColumnSetImpl;

/**
 * create time 2023/12/7 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnSetExpression {
    void accept(Setter s);
    default SQLColumnSetExpression then(SQLColumnSetExpression sqlColumnSetExpression){
        return new SQLColumnSetImpl(x -> {
            accept(x);
            sqlColumnSetExpression.accept(x);
        });
    }
    SQLColumnSetExpression empty=new SQLColumnSetImpl(x->{});
}
