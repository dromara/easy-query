package com.easy.query.test.entity.qq;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2023/12/29 16:01
 * 文件说明
 *
 * @author xuejiaming
 */

@Table(value = "todo_single_record")
@Data
public class TodoSingleRecord extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Column(primaryKey = true)
    private Long todoId;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String desc;
    /**
     * 截止时间
     */
    private LocalDateTime deadline;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     *    待办执行人
     */
    @Navigate(value = RelationTypeEnum.OneToMany
            , mappingClass = TodoExecutors.class
            , selfProperty = "todoId"
            , targetProperty = "todoId")
    private List<TodoExecutors> todoExecutorsList;

    /**
     * 待办参与人
     */
    @Navigate(value = RelationTypeEnum.OneToMany
            , mappingClass = TodoExecutors.class
            , selfProperty = "todoId"
            , targetProperty = "todoId")
    private List<TodoExecutors> todoExecutorsJoinList;


}