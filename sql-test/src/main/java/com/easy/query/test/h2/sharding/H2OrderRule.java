package com.easy.query.test.h2.sharding;

import com.easy.query.core.sharding.api.rule.mod.AbstractModTableRule;
import com.easy.query.test.h2.domain.H2Order;

/**
 * create time 2023/6/6 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2OrderRule extends AbstractModTableRule<H2Order> {
    @Override
    protected int mod() {
        return 5;
    }

    @Override
    protected int tailLength() {
        return 1;
    }
}
