package com.easy.query.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.api.query.ObjectQueryBuilder;
import com.easy.query.core.api.query.EasyQueryWhereConfiguration;
import com.easy.query.core.api.query.order.EasyOrderByBuilder;
import com.easy.query.core.api.query.order.EasyOrderByConfiguration;
import com.easy.query.entity.Topic;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: TopicRequest.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:50
 * @Created by xuejiaming
 */
@Data
public class TopicRequest implements EasyQueryWhereConfiguration<TopicRequest>, EasyOrderByConfiguration {

    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_LEFT_OPEN)
    private LocalDateTime createTimeBegin;
    private List<String> orders=new ArrayList<>();

    @Override
    public void configure(ObjectQueryBuilder<TopicRequest> objectQueryBuilder) {

        objectQueryBuilder.entity(Topic.class)
                .property(Topic::getCreateTime,TopicRequest::getCreateTimeBegin);
    }

    @Override
    public void configure(EasyOrderByBuilder easyOrderByBuilder) {
        easyOrderByBuilder.entity(Topic.class)
                .orderProperty(Topic::getCreateTime);
        for (String order : orders) {
            easyOrderByBuilder.orderBy(order,true);
        }
    }
}
