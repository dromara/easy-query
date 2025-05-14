package com.easy.query.test.dto;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * create time 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@EntityProxy //添加这个属性那么Topic对象会代理生成TopicProxy (需要idea build一下当前项目)
public class TopicMisDTO {
    private String id;
    private Integer stars1;
    @Column(value = "title")
    private String title1;
    private LocalDateTime createTime;
}
