package com.easy.query.test.entity.subjoin;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2025/2/20 15:12
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("sub_join_author")
@EntityProxy
public class SubJoinAuthor {

    @Column(primaryKey = true)
    private String id;

    private Integer age;

    private String name;

    private LocalDateTime createTime;
}
