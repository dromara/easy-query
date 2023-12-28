package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;

/**
 * create time 2023/12/7 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnSetExpression {
    void accept(Setter s);
    void accept(Selector s);
    void accept(AsSelector s);
}
