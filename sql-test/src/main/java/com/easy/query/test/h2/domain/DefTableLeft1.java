package com.easy.query.test.h2.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.h2.domain.proxy.DefTableLeft1Proxy;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * create time 2023/6/6 13:15
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_def_table_left1")
@Data
@EntityProxy
public class DefTableLeft1 implements ProxyEntityAvailable<DefTableLeft1 , DefTableLeft1Proxy> {
    @Column(primaryKey = true)
    private String id;
    private String defId;
    private String userName;
    private String nickname;
    private Boolean enable;
    private BigDecimal score;
    private String mobile;
    private String avatar;
    private Integer number;
    private Integer status;
    private LocalDateTime created;
    private String options;
}
