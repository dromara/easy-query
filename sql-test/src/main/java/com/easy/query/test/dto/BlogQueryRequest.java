package com.easy.query.test.dto;

import com.easy.query.api4j.dynamic.ObjectQuery4J;
import com.easy.query.api4j.dynamic.ObjectSort4J;
import com.easy.query.api4j.dynamic.condition.ObjectQueryBuilder4J;
import com.easy.query.api4j.dynamic.sort.ObjectSortBuilder4J;
import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.test.entity.BlogEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/6/11 13:44
 * @see com.easy.query.test.entity.BlogEntity
 *
 * @author xuejiaming
 */
@Data
public class BlogQueryRequest implements ObjectQuery4J<BlogQueryRequest,BlogEntity>, ObjectSort4J<BlogEntity> {

    /**
     * 标题
     */
    @EasyWhereCondition
    private String title;
    /**
     * 内容
     */
    @EasyWhereCondition
    private String content;
    /**
     * 点赞数
     */
    @EasyWhereCondition(type = EasyWhereCondition.Condition.EQUAL)
    private Integer star;
    /**
     * 发布时间
     */
    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_LEFT_CLOSED)
    private LocalDateTime publishTimeBegin;
    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_RIGHT_CLOSED)
    private LocalDateTime publishTimeEnd;
    /**
     * 评分
     */
    @EasyWhereCondition(type = EasyWhereCondition.Condition.GREATER_THAN_EQUAL)
    private BigDecimal score;
    /**
     * 状态
     */
    @EasyWhereCondition(type = EasyWhereCondition.Condition.LESS_THAN_EQUAL)
    private Integer status;
    /**
     * 排序
     */
    @EasyWhereCondition(type = EasyWhereCondition.Condition.GREATER_THAN)
    private BigDecimal order;
    /**
     * 是否置顶
     */
    @EasyWhereCondition(type = EasyWhereCondition.Condition.NOT_EQUAL)
    private Boolean isTop;
    @EasyWhereCondition(type = EasyWhereCondition.Condition.IN)
    private List<Integer> statusList=new ArrayList<>();
    @EasyWhereCondition(type = EasyWhereCondition.Condition.NOT_IN)
    private List<Integer> statusNotList=new ArrayList<>();

    @Override
    public void configure(ObjectQueryBuilder4J<BlogQueryRequest,BlogEntity> builder) {
        builder.property(BlogEntity::getUrl,BlogQueryRequest::getContent)
                .property(BlogEntity::getPublishTime,BlogQueryRequest::getPublishTimeBegin)
                .property(BlogEntity::getPublishTime,BlogQueryRequest::getPublishTimeEnd)
                .property(BlogEntity::getStatus,BlogQueryRequest::getStatusList)
                .property(BlogEntity::getStatus,BlogQueryRequest::getStatusNotList);
    }

    private List<String> orders=new ArrayList<>();
    @Override
    public void configure(ObjectSortBuilder4J<BlogEntity> builder) {
        for (String order : orders) {
            builder.orderBy(order,true);
        }
    }
}
