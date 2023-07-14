package com.easy.query.test.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
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
public class TopicRequest implements ObjectSort {

    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_LEFT_OPEN,propName = "createTime")
    private LocalDateTime createTimeBegin;
    private List<String> orders=new ArrayList<>();


    @Override
    public void configure(ObjectSortBuilder builder) {
        builder.allowed("createTime");
        for (String order : orders) {
            builder.orderBy(order,true);
        }
    }
}
