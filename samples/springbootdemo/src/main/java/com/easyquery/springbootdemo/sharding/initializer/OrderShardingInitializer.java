package com.easyquery.springbootdemo.sharding.initializer;

import com.easy.query.core.sharding.api.initializer.mod.AbstractShardingTableModInitializer;
import com.easyquery.springbootdemo.domain.OrderEntity;
import org.springframework.stereotype.Component;

/**
 * create time 2023/5/24 23:51
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderShardingInitializer extends AbstractShardingTableModInitializer<OrderEntity> {
    /**
     * 设置模几我们模5就设置5
     *
     * @return
     */
    @Override
    protected int mod() {
        return 5;
    }

    /**
     * 编写模5后的尾巴长度默认我们设置2就是左补0
     *
     * @return
     */
    @Override
    protected int tailLength() {
        return 2;
    }
}
