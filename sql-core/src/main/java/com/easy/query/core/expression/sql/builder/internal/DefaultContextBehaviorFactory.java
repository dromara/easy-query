package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.enums.EasyBehaviorEnum;

/**
 * create time 2026/3/9 16:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultContextBehaviorFactory implements ContextBehaviorFactory{
    @Override
    public EasyBehavior create() {
        EasyBehavior easyBehavior = new EasyBehavior();
        easyBehavior.addBehavior(EasyBehaviorEnum.USE_TRACKING);
        return easyBehavior;
    }
}
