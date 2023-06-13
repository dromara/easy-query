package com.easyquery.springbootdemo.sharding.route;

import com.easy.query.core.sharding.api.route.mod.AbstractModTableRoute;
import com.easyquery.springbootdemo.domain.OrderEntity;
import org.springframework.stereotype.Component;

/**
 * create time 2023/5/24 23:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderTableRoute extends AbstractModTableRoute<OrderEntity> {
    @Override
    protected int mod() {
        return 5;
    }

    @Override
    protected int tailLength() {
        return 2;
    }
}
