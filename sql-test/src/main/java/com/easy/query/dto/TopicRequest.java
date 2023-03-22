package com.easy.query.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.api.query.ObjectQueryBuilder;
import com.easy.query.core.api.query.ObjectQueryMapping;
import com.easy.query.entity.Topic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName: TopicRequest.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:50
 * @Created by xuejiaming
 */
@Data
public class TopicRequest implements ObjectQueryMapping<TopicRequest> {

    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_LEFT_OPEN)
    private LocalDateTime createTimeBegin;

    @Override
    public void configure(ObjectQueryBuilder<TopicRequest> objectQueryBuilder) {

        objectQueryBuilder.EntityProperty(Topic.class)
                .property(Topic::getCreateTime,TopicRequest::getCreateTimeBegin);
    }
}
