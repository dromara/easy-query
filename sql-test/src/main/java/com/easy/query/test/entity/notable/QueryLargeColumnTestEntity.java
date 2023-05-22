package com.easy.query.test.entity.notable;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

/**
 * create time 2023/5/22 21:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("query_large_column_test")
public class QueryLargeColumnTestEntity {
    @Column(primaryKey = true)
    private String id;

    private String name;
    @Column(large = true)
    private String content;
}
