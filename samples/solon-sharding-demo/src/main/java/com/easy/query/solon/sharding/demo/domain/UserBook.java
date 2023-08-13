package com.easy.query.solon.sharding.demo.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

/**
 * create time 2023/8/12 21:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("user_book")
public class UserBook {
    @Column(primaryKey = true)
    private String id;
    private String userId;
    private String name;
}
