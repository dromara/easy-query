package com.easyquery.springbootdemo.sharding.rule;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.rule.datasource.abstraction.AbstractDataSourceRouteRule;
import com.easyquery.springbootdemo.domain.OrderDsEntity;
import org.springframework.stereotype.Component;


/**
 * create time 2023/6/4 21:10
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderDsRouteRule extends AbstractDataSourceRouteRule<OrderDsEntity> {
    @Override
    protected RouteFunction<String> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        int i = shardingValue.toString().hashCode();

        int dsNumber = Math.abs(i % 4);

        String dataSource = "ds" + dsNumber;
        switch (shardingOperator) {
            case EQUAL:
                return ds -> dataSource.compareToIgnoreCase(ds) == 0;
            default:
                return t -> true;
        }
    }
}
