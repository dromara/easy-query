package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;

/**
 * create time 2023/12/1 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectAs extends SQLSelect, SQLGroupSelect {
    default SQLSelectAs then(SQLSelectAs sqlSelectAs) {
        return new SQLSelectAsImpl(x -> {
            accept(x);
            sqlSelectAs.accept(x);
        });
    }

    default void accept(AsSelector f) {
        f.column(this.getTable(), this.value());
    }
}
