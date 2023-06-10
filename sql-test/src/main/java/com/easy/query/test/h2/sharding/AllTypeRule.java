package com.easy.query.test.h2.sharding;

import com.easy.query.core.sharding.api.rule.mod.AbstractModTableRule;
import com.easy.query.test.h2.domain.ALLTYPESharding;

/**
 * create time 2023/6/10 10:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class AllTypeRule extends AbstractModTableRule<ALLTYPESharding> {
    @Override
    protected int mod() {
        return 2;
    }

    @Override
    protected int tailLength() {
        return 1;
    }
}
