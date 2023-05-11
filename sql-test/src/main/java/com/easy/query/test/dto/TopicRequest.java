package com.easy.query.test.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.api.dynamic.where.EasyWhereBuilder;
import com.easy.query.core.api.dynamic.where.EasyWhere;
import com.easy.query.core.api.dynamic.order.EasyOrderByBuilder;
import com.easy.query.core.api.dynamic.order.EasyOrderBy;
import com.easy.query.test.entity.Topic;
import lombok.Data;
import org.junit.Ignore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: TopicRequest.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:50
 * @author xuejiaming
 */
@Ignore
@Data
public class TopicRequest implements EasyWhere<TopicRequest>, EasyOrderBy {

    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_LEFT_OPEN)
    private LocalDateTime createTimeBegin;
    private List<String> orders=new ArrayList<>();

    @Override
    public void configure(EasyWhereBuilder<TopicRequest> builder) {

        builder.mapTo(Topic.class)
                .property(Topic::getCreateTime,TopicRequest::getCreateTimeBegin);
    }

    @Override
    public void configure(EasyOrderByBuilder builder) {
        builder.mapTo(Topic.class)
                .orderProperty(Topic::getCreateTime);
        for (String order : orders) {
            builder.orderBy(order,true);
        }
    }
}
