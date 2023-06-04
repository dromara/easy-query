package com.easyquery.springbootdemo.sharding.rule;

import com.easy.query.core.sharding.api.rule.mod.AbstractModTableRule;
import com.easyquery.springbootdemo.domain.OrderEntity;
import org.springframework.stereotype.Component;

/**
 * create time 2023/5/24 23:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderTableRouteRule extends AbstractModTableRule<OrderEntity> {
    @Override
    protected int mod() {
        return 5;
    }

    @Override
    protected int tailLength() {
        return 2;
    }
}
