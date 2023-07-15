package com.easy.query.test.dto;

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
public class BlogQuery1Request {

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 发布时间
     */
    private LocalDateTime publishTimeBegin;
    private LocalDateTime publishTimeEnd;
    /**
     * 评分
     */
    private BigDecimal score;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private BigDecimal order;
    /**
     * 是否置顶
     */
    private Boolean isTop;
    private List<Integer> statusList=new ArrayList<>();
    private List<Integer> statusNotList=new ArrayList<>();
}
