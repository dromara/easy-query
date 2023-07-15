package com.easy.query.test.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/14 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class BlogQuery2Request {

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
    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_LEFT_CLOSED,propName = "publishTime")
    private LocalDateTime publishTimeBegin;
    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_RIGHT_CLOSED,propName = "publishTime")
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
    @EasyWhereCondition(type = EasyWhereCondition.Condition.EQUAL)
    private BigDecimal order;
    /**
     * 是否置顶
     */
    @EasyWhereCondition(type = EasyWhereCondition.Condition.NOT_EQUAL)
    private Boolean isTop;
    @EasyWhereCondition(type = EasyWhereCondition.Condition.IN,propName = "status")
    private List<Integer> statusList=new ArrayList<>();
    @EasyWhereCondition(type = EasyWhereCondition.Condition.NOT_IN,propName = "status")
    private List<Integer> statusNotList=new ArrayList<>();
}
