package com.easy.query.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.api.dynamic.where.EasyDynamicWhereBuilder;
import com.easy.query.core.api.dynamic.where.EasyDynamicWhereConfiguration;
import com.easy.query.core.api.dynamic.order.EasyDynamicOrderByBuilder;
import com.easy.query.core.api.dynamic.order.EasyDynamicOrderByConfiguration;
import com.easy.query.entity.Topic;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: TopicRequest.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:50
 * @author xuejiaming
 */
@Data
public class TopicRequest implements EasyDynamicWhereConfiguration<TopicRequest>, EasyDynamicOrderByConfiguration {

    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_LEFT_OPEN)
    private LocalDateTime createTimeBegin;
    private List<String> orders=new ArrayList<>();

    @Override
    public void configure(EasyDynamicWhereBuilder<TopicRequest> builder) {

        builder.entity(Topic.class)
                .property(Topic::getCreateTime,TopicRequest::getCreateTimeBegin);
    }

    @Override
    public void configure(EasyDynamicOrderByBuilder builder) {
        builder.entity(Topic.class)
                .orderProperty(Topic::getCreateTime);
        for (String order : orders) {
            builder.orderBy(order,true);
        }
    }
}
