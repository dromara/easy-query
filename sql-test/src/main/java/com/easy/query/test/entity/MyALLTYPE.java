package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MyALLTYPEProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

/**
 * create time 2023/6/10 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_all_type")
@EntityProxy
@FieldNameConstants
//@Accessors(chain = true)
public class MyALLTYPE implements ProxyEntityAvailable<MyALLTYPE , MyALLTYPEProxy> {
    @Column(primaryKey = true)
    private String id;
    private BigDecimal isNumberDecimal;
    private Integer isNumberInteger;
    private Boolean isEnable;
    private int isNumberIntegerBasic;
    private boolean isEnableBasic;
    private LocalDateTime isTimeLocalDateTime;
    private String isValue;
}
