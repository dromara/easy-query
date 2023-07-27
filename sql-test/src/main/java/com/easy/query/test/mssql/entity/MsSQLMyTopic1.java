package com.easy.query.test.mssql.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/7/27 17:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "MyTopic")
public class MsSQLMyTopic1 {

    @Column(primaryKey = true)
    private String Id;
    private Integer Stars;
    private String Title;
    private LocalDateTime CreateTime;
}
