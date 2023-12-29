package com.easy.query.test.entity.qq;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * create time 2023/12/29 16:00
 * 文件说明
 *
 * @author xuejiaming
 */

@Data
@Table(value = "todo_executors")
public class TodoExecutors extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 待办id
     */
    @Column(primaryKey = true)
    private Long id;
    private Long todoId;
    /**
     * 0=执行人,1参与人
     */
    private Integer type;
    /**
     * type=0就是执行人，type=1就是参与人
     */
    private Long userId;

}