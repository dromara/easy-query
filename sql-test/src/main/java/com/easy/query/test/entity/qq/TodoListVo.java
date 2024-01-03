package com.easy.query.test.entity.qq;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2023/12/29 16:01
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class TodoListVo {
    /**
     * 主键
     */
    @Column(primaryKey = true)
    private Long todoId;
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
